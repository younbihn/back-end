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
import com.example.demo.exception.impl.AlreadyExistedApplyException;
import com.example.demo.exception.impl.ClosedMatchingException;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.type.AgeGroup;
import com.example.demo.type.ApplyStatus;
import com.example.demo.type.GenderType;
import com.example.demo.type.MatchingType;
import com.example.demo.type.RecruitStatus;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
                .password("1234")
                .nickname("nick")
                .email("email@gmail.com")
                .phoneNumber("010-1234-5678")
                .gender(GenderType.FEMALE)
                .ntrp(BigDecimal.valueOf(1.0))
                .locationSi("안양시")
                .locationGu("동안구")
                .ageGroup(AgeGroup.TWENTIES)
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .isPhoneVerified(true)
                .build();

        Matching matching = Matching.builder()
                .id(2L)
                .siteUser(siteUser)
                .title("경기해요.")
                .content("경기 내용입니다.")
                .location("oo 구장")
                .date(Date.valueOf("2023-11-10"))
                .startTime(Time.valueOf("15:00:00"))
                .endTime(Time.valueOf("17:00:00"))
                .recruitNum(3)
                .cost(1000)
                .isReserved(true)
                .ntrp("3.0~4.0")
                .age("20~30")
                .recruitStatus(RecruitStatus.OPEN)
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .matchingType(MatchingType.DOUBLE)
                .applyNum(0)
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
                        .createTime(Timestamp.valueOf(LocalDateTime.now()))
                        .status(ApplyStatus.PENDING)
                        .build());
        ArgumentCaptor<Apply> captor = ArgumentCaptor.forClass(Apply.class);

        // when
        ApplyDto applyDto = applyService.apply(1L, 2L);

        // then
        verify(applyRepository, times(1)).save(captor.capture()); // save 메서드가 한 번 실행되는 지 검증
        assertEquals(1L, applyDto.getSiteUser().getId());
        assertEquals(2L, applyDto.getMatching().getId());
    }

    @Test
    void applyFailByDuplication() {
        //given
        SiteUser siteUser = SiteUser.builder()
                .id(1L)
                .password("1234")
                .nickname("nick")
                .email("email@gmail.com")
                .phoneNumber("010-1234-5678")
                .gender(GenderType.FEMALE)
                .ntrp(BigDecimal.valueOf(1.0))
                .locationSi("안양시")
                .locationGu("동안구")
                .ageGroup(AgeGroup.TWENTIES)
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .isPhoneVerified(true)
                .build();

        Matching matching = Matching.builder()
                .id(2L)
                .siteUser(siteUser)
                .title("경기해요.")
                .content("경기 내용입니다.")
                .location("oo 구장")
                .date(Date.valueOf("2023-11-10"))
                .startTime(Time.valueOf("15:00:00"))
                .endTime(Time.valueOf("17:00:00"))
                .recruitNum(3)
                .cost(1000)
                .isReserved(true)
                .ntrp("3.0~4.0")
                .age("20~30")
                .recruitStatus(RecruitStatus.OPEN)
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .matchingType(MatchingType.DOUBLE)
                .applyNum(0)
                .build();

        Apply apply = Apply.builder()
                .id(1L)
                .matching(matching)
                .siteUser(siteUser)
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
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
        AlreadyExistedApplyException exception = assertThrows(AlreadyExistedApplyException.class,
                () -> applyService.apply(1L, 2L));

        // then
        assertEquals(exception.getMessage(), "이미 참여 신청한 경기입니다.");
    }

    @Test
    void applyFailByRecruitClosed() {
        //given
        SiteUser siteUser = SiteUser.builder()
                .id(1L)
                .password("1234")
                .nickname("nick")
                .email("email@gmail.com")
                .phoneNumber("010-1234-5678")
                .gender(GenderType.FEMALE)
                .ntrp(BigDecimal.valueOf(1.0))
                .locationSi("안양시")
                .locationGu("동안구")
                .ageGroup(AgeGroup.TWENTIES)
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .isPhoneVerified(true)
                .build();

        Matching matching = Matching.builder()
                .id(2L)
                .siteUser(siteUser)
                .title("경기해요.")
                .content("경기 내용입니다.")
                .location("oo 구장")
                .date(Date.valueOf("2023-11-10"))
                .startTime(Time.valueOf("15:00:00"))
                .endTime(Time.valueOf("17:00:00"))
                .recruitNum(3)
                .cost(1000)
                .isReserved(true)
                .ntrp("3.0~4.0")
                .age("20~30")
                .recruitStatus(RecruitStatus.CLOSED)
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .matchingType(MatchingType.DOUBLE)
                .applyNum(0)
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
                .password("1234")
                .nickname("nick")
                .email("email@gmail.com")
                .phoneNumber("010-1234-5678")
                .gender(GenderType.FEMALE)
                .ntrp(BigDecimal.valueOf(1.0))
                .locationSi("안양시")
                .locationGu("동안구")
                .ageGroup(AgeGroup.TWENTIES)
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .isPhoneVerified(true)
                .build();

        Matching matching = Matching.builder()
                .id(2L)
                .siteUser(siteUser)
                .title("경기해요.")
                .content("경기 내용입니다.")
                .location("oo 구장")
                .date(Date.valueOf("2023-11-10"))
                .startTime(Time.valueOf("15:00:00"))
                .endTime(Time.valueOf("17:00:00"))
                .recruitNum(3)
                .cost(1000)
                .isReserved(true)
                .ntrp("3.0~4.0")
                .age("20~30")
                .recruitStatus(RecruitStatus.OPEN)
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .matchingType(MatchingType.DOUBLE)
                .applyNum(0)
                .build();

        Apply apply = Apply.builder()
                .id(1L)
                .matching(matching)
                .siteUser(siteUser)
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .status(ApplyStatus.PENDING)
                .build();

        given(applyRepository.findById(anyLong()))
                .willReturn(Optional.of(apply));

        // when
        ApplyDto applyDto = applyService.cancel(1L, 1L);

        // then
        assertEquals(ApplyStatus.CANCELED, applyDto.getApplyStatus());
    }

}