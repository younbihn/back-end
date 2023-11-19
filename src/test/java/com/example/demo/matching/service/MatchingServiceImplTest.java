package com.example.demo.matching.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.common.FindEntity;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.notification.service.NotificationService;
import com.example.demo.siteuser.repository.SiteUserRepository;
import com.example.demo.type.AgeGroup;
import com.example.demo.type.GenderType;
import com.example.demo.type.MatchingType;
import com.example.demo.type.Ntrp;
import com.example.demo.type.RecruitStatus;
import java.lang.annotation.Native;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class MatchingServiceImplTest {
    @Mock
    private MatchingRepository matchingRepository;

    @Mock
    private SiteUserRepository siteUserRepository;

    @Mock
    private ApplyRepository applyRepository;

    @Mock
    FindEntity findEntity;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private MatchingServiceImpl matchingService;

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("유저 아이디에 해당하는 유저와 함께 매칭이 저장됨")
    void create() {
        //given
        SiteUser siteUser = makeSiteUser();
        MatchingDetailDto matchingDetailDto = makeMatchingDetailDto();
        ApplyDto applyDto = makeApplyDto(siteUser, Matching.fromDto(matchingDetailDto, siteUser));
        given(siteUserRepository.findById(anyLong()))
                .willReturn(Optional.of(siteUser));
        given(matchingRepository.save(any(Matching.class)))
                .willReturn(Matching.fromDto(matchingDetailDto, siteUser));
        given(applyRepository.save(any(Apply.class)))
                .willReturn(Apply.fromDto(applyDto));

        //when
        Matching savedMatching = matchingService.create(siteUser.getEmail(), matchingDetailDto);

        //then
        assertThat(savedMatching.getSiteUser().getId()).isEqualTo(siteUser.getId());
        assertThat(savedMatching.getTitle()).isEqualTo(matchingDetailDto.getTitle());
        assertThat(savedMatching.getContent()).isEqualTo(matchingDetailDto.getContent());
        assertThat(savedMatching.getLocation()).isEqualTo(matchingDetailDto.getLocation());
        assertThat(savedMatching.getLat()).isEqualTo(matchingDetailDto.getLat());
        assertThat(savedMatching.getLon()).isEqualTo(matchingDetailDto.getLon());
        assertThat(savedMatching.getLocationImg()).isEqualTo(matchingDetailDto.getLocationImg());
        assertThat(savedMatching.getDate()).isEqualTo(matchingDetailDto.getDate());
        assertThat(savedMatching.getStartTime()).isEqualTo(matchingDetailDto.getStartTime());
        assertThat(savedMatching.getEndTime()).isEqualTo(matchingDetailDto.getEndTime());
        assertThat(savedMatching.getRecruitNum()).isEqualTo(matchingDetailDto.getRecruitNum());
        assertThat(savedMatching.getCost()).isEqualTo(matchingDetailDto.getCost());
        assertThat(savedMatching.getIsReserved()).isEqualTo(matchingDetailDto.getIsReserved());
        assertThat(savedMatching.getNtrp()).isEqualTo(matchingDetailDto.getNtrp());
        assertThat(savedMatching.getAge()).isEqualTo(matchingDetailDto.getAgeGroup());
        assertThat(savedMatching.getRecruitStatus()).isEqualTo(matchingDetailDto.getRecruitStatus());
        assertThat(savedMatching.getMatchingType()).isEqualTo(matchingDetailDto.getMatchingType());
    }



    @Test
    @DisplayName("수정한 값이 제대로 저장됨")
    void update() {
        // given
        SiteUser siteUser = makeSiteUser();
        Matching matching = makeMatching(siteUser);
        MatchingDetailDto matchingDetailDto = makeMatchingDetailDto();
        given(siteUserRepository.findById(anyLong()))
                .willReturn(Optional.of(siteUser));
        given(matchingRepository.findById(anyLong()))
                .willReturn(Optional.of(matching));
        given(matchingRepository.existsByIdAndSiteUser(anyLong(), any(SiteUser.class)))
                .willReturn(true);
        given(matchingRepository.save(any(Matching.class)))
                .willReturn(Matching.fromDto(matchingDetailDto, siteUser));

        //when
        Matching savedMatching = matchingService.update(siteUser.getEmail(), 1L, matchingDetailDto);

        //then
        assertThat(savedMatching.getSiteUser().getId()).isEqualTo(siteUser.getId());
        assertThat(savedMatching.getTitle()).isEqualTo(matchingDetailDto.getTitle());
        assertThat(savedMatching.getContent()).isEqualTo(matchingDetailDto.getContent());
        assertThat(savedMatching.getLocation()).isEqualTo(matchingDetailDto.getLocation());
        assertThat(savedMatching.getLat()).isEqualTo(matchingDetailDto.getLat());
        assertThat(savedMatching.getLon()).isEqualTo(matchingDetailDto.getLon());
        assertThat(savedMatching.getLocationImg()).isEqualTo(matchingDetailDto.getLocationImg());
        assertThat(savedMatching.getDate()).isEqualTo(matchingDetailDto.getDate());
        assertThat(savedMatching.getStartTime()).isEqualTo(matchingDetailDto.getStartTime());
        assertThat(savedMatching.getEndTime()).isEqualTo(matchingDetailDto.getEndTime());
        assertThat(savedMatching.getRecruitNum()).isEqualTo(matchingDetailDto.getRecruitNum());
        assertThat(savedMatching.getCost()).isEqualTo(matchingDetailDto.getCost());
        assertThat(savedMatching.getIsReserved()).isEqualTo(matchingDetailDto.getIsReserved());
        assertThat(savedMatching.getNtrp()).isEqualTo(matchingDetailDto.getNtrp());
        assertThat(savedMatching.getAge()).isEqualTo(matchingDetailDto.getAgeGroup());
        assertThat(savedMatching.getRecruitStatus()).isEqualTo(matchingDetailDto.getRecruitStatus());
        assertThat(savedMatching.getMatchingType()).isEqualTo(matchingDetailDto.getMatchingType());
    }

    // 이 이후는 jpa에서 기본으로 제공하는 method로만 이루어져서 테스트 안해도 될 듯
    @Test
    void delete() {
    }

    @Test
    void getList() {
    }

    @Test
    void getDetail() {
    }

    private SiteUser makeSiteUser() {
        return SiteUser.builder()
                .id(1L)
                .password("password123")
                .nickname("nickname")
                .email("example@example.com")
                .phoneNumber("010-1234-5678")
                .mannerScore(10)
                .penaltyScore(0)
                .gender(GenderType.MALE)
                .ntrp(Ntrp.DEVELOPMENT)
                .address("서울")
                .zipCode("12345")
                .ageGroup(AgeGroup.TWENTIES)
                .profileImg("http://example.com/img.jpg")
                .createDate(LocalDateTime.now())
                .isPhoneVerified(true)
                .hostedMatches(new ArrayList<>())
                .applies(new ArrayList<>())
                .notifications(new ArrayList<>())
                .build();
    }

    private ApplyDto makeApplyDto(SiteUser siteUser, Matching matching) {
        return ApplyDto.builder()
                .matching(matching)
                .siteUser(siteUser)
                .build();
    }

    private MatchingDetailDto makeMatchingDetailDto() {
        return MatchingDetailDto.builder()
                .id(1L)
                .title("제목")
                .content("본문")
                .location("장소")
                .locationImg("구장 이미지 주소")
                .date("2023-11-11")
                .startTime("10:00")
                .endTime("12:00")
                .recruitDueDate("2023-11-10")
                .recruitDueTime("23")
                .recruitNum(5)
                .cost(5000)
                .isReserved(false)
                .ntrp(Ntrp.DEVELOPMENT)
                .ageGroup(AgeGroup.TWENTIES)
                .recruitStatus(RecruitStatus.OPEN)
                .matchingType(MatchingType.SINGLE)
                .build();
    }

    private MatchingPreviewDto makeMatchingPreviewDto() {
        return MatchingPreviewDto.builder()
                .isReserved(true)
                .matchingType(MatchingType.SINGLE)
                .ntrp(Ntrp.ADVANCE)
                .title("제목")
                .matchingStartDateTime("시작 날짜")
                .build();
    }

    private Matching makeMatching(SiteUser siteUser) {
        return Matching.builder()
                .siteUser(siteUser)
                .createTime(LocalDateTime.now())
                .age(AgeGroup.FORTIES)
                .content("내용")
                .confirmedNum(1)
                .matchingType(MatchingType.SINGLE)
                .ntrp(Ntrp.PRO)
                .isReserved(true)
                .recruitDueDateTime(LocalDateTime.now().plusDays(3))
                .cost(5000)
                .location("서울특별시 중구 을지로 66")
                .lat(37.56556383681641)
                .lon(126.98540998152264)
                .recruitStatus(RecruitStatus.OPEN)
                .title("같이 테니스 치실분 구해요")
                .siteUser(new SiteUser())
                .build();
    }
}
