package com.example.demo.apply.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import com.example.demo.exception.impl.AlreadyCanceledApplyException;
import com.example.demo.exception.impl.AlreadyExistedApplyException;
import com.example.demo.exception.impl.ClosedMatchingException;
import com.example.demo.exception.impl.NonExistedApplyException;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.response.ResponseDto;
import com.example.demo.type.ApplyStatus;
import com.example.demo.type.RecruitStatus;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplyServiceTest {

    @Mock
    private ApplyRepository applyRepository;
    @Mock
    private SiteUserRepository siteUserRepository;

    @Mock
    private MatchingRepository matchingRepository;

    @InjectMocks
    private ApplyServiceImpl applyService;

    @Test
    void applySuccess() {
        //given
        SiteUser siteUser = SiteUser.builder()
                .id(1L)
                .build();

        Matching matching = Matching.builder()
                .id(2L)
                .recruitStatus(RecruitStatus.OPEN)
                .build();


        given(siteUserRepository.findById(anyLong()))
                .willReturn(Optional.of(siteUser));

        given(matchingRepository.findById(anyLong()))
                .willReturn(Optional.of(matching));

        given(applyRepository.save(any()))
                .willReturn(Apply.builder()
                        .id(1L)
                        .matching(matching)
                        .siteUser(siteUser)
                        .build());
        ArgumentCaptor<Apply> captor = ArgumentCaptor.forClass(Apply.class);

        // when
        ResponseDto<ApplyDto> applyDto = applyService.apply(1L, 2L);

        // then
        verify(applyRepository, times(1)).save(captor.capture()); // save 메서드가 한 번 실행되는 지 검증
        assertEquals(1L, applyDto.getData().getSiteUser().getId());
        assertEquals(2L, applyDto.getData().getMatching().getId());
    }

    @Test
    void applyFailByDuplication() {
        //given
        SiteUser siteUser = SiteUser.builder()
                .id(1L)
                .build();

        Matching matching = Matching.builder()
                .id(2L)
                .recruitStatus(RecruitStatus.OPEN)
                .build();

        Apply apply = Apply.builder()
                .id(1L)
                .matching(matching)
                .siteUser(siteUser)
                .status(ApplyStatus.PENDING)
                .build();

        given(siteUserRepository.findById(anyLong()))
                .willReturn(Optional.of(siteUser));

        given(matchingRepository.findById(anyLong()))
                .willReturn(Optional.of(matching));

        given(applyRepository.existsBySiteUser_IdAndMatching_Id(anyLong(), anyLong()))
                .willReturn(true);

        given(applyRepository.findBySiteUser_IdAndMatching_Id(anyLong(), anyLong()))
                .willReturn(Optional.of(apply));

        // when
        ResponseDto<ApplyDto> applyDto = applyService.apply(1L, 2L);

        // then
        assertEquals("이미 신청한 매칭 내역이 존재합니다.", applyDto.getMessage());
    }

    @Test
    void applyFailByRecruitClosed() {
        //given
        SiteUser siteUser = SiteUser.builder()
                .id(1L)
                .build();

        Matching matching = Matching.builder()
                .id(2L)
                .recruitStatus(RecruitStatus.CLOSED)
                .build();

        given(siteUserRepository.findById(anyLong()))
                .willReturn(Optional.of(siteUser));

        given(matchingRepository.findById(anyLong()))
                .willReturn(Optional.of(matching));

        // when
        ClosedMatchingException exception = assertThrows(ClosedMatchingException.class,
                () -> applyService.apply(1L, 2L));

        // then
        assertEquals(exception.getMessage(), "신청 마감된 경기입니다.");
    }

    @Test
    void applyCancelSuccess() {
        //given
        SiteUser siteUser = SiteUser.builder()
                .id(1L)
                .build();

        Matching matching = Matching.builder()
                .id(2L)
                .recruitStatus(RecruitStatus.OPEN)
                .build();

        Apply apply = Apply.builder()
                .id(1L)
                .matching(matching)
                .siteUser(siteUser)
                .status(ApplyStatus.PENDING)
                .build();

        given(applyRepository.findById(anyLong()))
                .willReturn(Optional.of(apply));
        given(matchingRepository.findById(anyLong()))
                .willReturn(Optional.of(matching));

        // when
        ResponseDto<ApplyDto> applyDto = applyService.cancel(1L);

        // then
        assertEquals(ApplyStatus.CANCELED, applyDto.getData().getApplyStatus());
    }

    @Test
    void applyCancelFailByNonExistedApply() {
        // given
        // when
        NonExistedApplyException exception = assertThrows(NonExistedApplyException.class,
                () -> applyService.cancel(1L));

        // then
        assertEquals(exception.getMessage(), "참가 신청 내역이 없습니다. 이미 삭제된 경기로 예상됩니다.");
    }

    @Test
    void applyCancelFailByAlreadyCanceledApply() {
        // given
        Apply apply = Apply.builder()
                .id(1L)
                .status(ApplyStatus.CANCELED)
                .build();

        given(applyRepository.findById(anyLong()))
                .willReturn(Optional.of(apply));
        // when
        AlreadyCanceledApplyException exception = assertThrows(AlreadyCanceledApplyException.class,
                () -> applyService.cancel(1L));

        // then
        assertEquals(exception.getMessage(), "이미 참가 신청이 취소된 경기입니다.");
    }

    @Test
    void applyCancelFailedByDateIssue() {
        // given
        Apply apply = Apply.builder()
                .id(1L)
                .matching(Matching.builder().id(1L).build())
                .status(ApplyStatus.ACCEPTED)
                .build();

        given(applyRepository.findById(anyLong()))
                .willReturn(Optional.of(apply));

        given(matchingRepository.findById(anyLong()))
                .willReturn(Optional.of(Matching.builder()
                                .id(1L)
                                .recruitStatus(RecruitStatus.CLOSED)
                                .date(Date.valueOf(LocalDate.now()))
                        .build()));
        // when
        ResponseDto<ApplyDto> applyDto = applyService.cancel(1L);

        // then
        assertEquals("매칭 당일에는 매칭 취소가 불가능합니다.", applyDto.getMessage());
    }

//    @Test
//    void applyAcceptSuccess() {
//        //given
//        applyRepository.save(Apply.builder()
//                .id(1L)
//                .build());
//        applyRepository.save(Apply.builder()
//                .id(2L)
//                .build());
//        matchingRepository.save(Matching.builder().id(1L).recruitNum(3).build());
//
//        List<Long> appliedList = new ArrayList<>();
//        appliedList.add(1L);
//
//        List<Long> confirmedList = new ArrayList<>();
//        appliedList.add(2L);
//
//        // when
//        ResponseDto<Object> applyDto = applyService.accept(appliedList, confirmedList, 1L);
//
//        // then
//        assertEquals("수락 확정을 진행하였습니다.", applyDto.getMessage());
//    }

}