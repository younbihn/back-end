package com.example.demo.matching.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.type.AgeGroup;
import com.example.demo.type.GenderType;
import com.example.demo.type.MatchingType;
import com.example.demo.type.RecruitStatus;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MatchingServiceImplTest {
    @Mock
    private MatchingRepository matchingRepository;

    @Mock
    private SiteUserRepository siteUserRepository;

    @InjectMocks
    private MatchingServiceImpl matchingService;

    @Test
    @DisplayName("유저 아이디에 해당하는 유저와 함께 매칭이 저장됨")
    void create() {
        //given
        SiteUser siteUser = makeSiteUser();
        MatchingDetailDto matchingDetailDto = makeMatchingDetailDto();
        given(siteUserRepository.findById(anyLong()))
                .willReturn(Optional.of(siteUser));
        given(matchingRepository.save(any(Matching.class)))
                .willReturn(Matching.fromDto(matchingDetailDto, siteUser));

        //when
        MatchingDetailDto savedDto = matchingService.create(1L, matchingDetailDto);

        //then
        assertThat(savedDto.getCreatorUserId()).isEqualTo(siteUser.getId());
        assertThat(savedDto.getCreateTime()).isNotNull();

        assertThat(savedDto.getTitle()).isEqualTo(matchingDetailDto.getTitle());
        assertThat(savedDto.getContent()).isEqualTo(matchingDetailDto.getContent());
        assertThat(savedDto.getLocation()).isEqualTo(matchingDetailDto.getLocation());
        assertThat(savedDto.getLocationImg()).isEqualTo(matchingDetailDto.getLocationImg());

        assertThat(savedDto.getDate()).isEqualTo(matchingDetailDto.getDate());
        assertThat(savedDto.getStartTime()).isEqualTo(matchingDetailDto.getStartTime());
        assertThat(savedDto.getEndTime()).isEqualTo(matchingDetailDto.getEndTime());
        assertThat(savedDto.getRecruitNum()).isEqualTo(matchingDetailDto.getRecruitNum());

        assertThat(savedDto.getCost()).isEqualTo(matchingDetailDto.getCost());
        assertThat(savedDto.getIsReserved()).isEqualTo(matchingDetailDto.getIsReserved());
        assertThat(savedDto.getNtrp()).isEqualTo(matchingDetailDto.getNtrp());
        assertThat(savedDto.getAgeGroup()).isEqualTo(matchingDetailDto.getAgeGroup());
        assertThat(savedDto.getRecruitStatus()).isEqualTo(matchingDetailDto.getRecruitStatus());
        assertThat(savedDto.getMatchingType()).isEqualTo(matchingDetailDto.getMatchingType());
        assertThat(savedDto.getApplyNum()).isEqualTo(matchingDetailDto.getApplyNum());
        assertThat(savedDto.getCreateTime()).isEqualTo(matchingDetailDto.getCreateTime());
    }

    @Test
    @DisplayName("수정한 값이 제대로 저장됨")
    void update() {
        //given
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
        MatchingDetailDto savedDto = matchingService.update(1L, 1L, matchingDetailDto);

        //then
        assertThat(savedDto.getCreatorUserId()).isEqualTo(siteUser.getId());
        assertThat(savedDto.getCreateTime()).isNotNull();

        assertThat(savedDto.getTitle()).isEqualTo(matchingDetailDto.getTitle());
        assertThat(savedDto.getContent()).isEqualTo(matchingDetailDto.getContent());
        assertThat(savedDto.getLocation()).isEqualTo(matchingDetailDto.getLocation());
        assertThat(savedDto.getLocationImg()).isEqualTo(matchingDetailDto.getLocationImg());

        assertThat(savedDto.getDate()).isEqualTo(matchingDetailDto.getDate());
        assertThat(savedDto.getStartTime()).isEqualTo(matchingDetailDto.getStartTime());
        assertThat(savedDto.getEndTime()).isEqualTo(matchingDetailDto.getEndTime());

        assertThat(savedDto.getRecruitNum()).isEqualTo(matchingDetailDto.getRecruitNum());
        assertThat(savedDto.getCost()).isEqualTo(matchingDetailDto.getCost());
        assertThat(savedDto.getIsReserved()).isEqualTo(matchingDetailDto.getIsReserved());
        assertThat(savedDto.getNtrp()).isEqualTo(matchingDetailDto.getNtrp());
        assertThat(savedDto.getAgeGroup()).isEqualTo(matchingDetailDto.getAgeGroup());
        assertThat(savedDto.getRecruitStatus()).isEqualTo(matchingDetailDto.getRecruitStatus());
        assertThat(savedDto.getMatchingType()).isEqualTo(matchingDetailDto.getMatchingType());
        assertThat(savedDto.getApplyNum()).isEqualTo(matchingDetailDto.getApplyNum());
        assertThat(savedDto.getCreateTime()).isEqualTo(matchingDetailDto.getCreateTime());
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
                .ntrp(new BigDecimal("3.5"))
                .locationSi("서울")
                .locationGu("강남구")
                .ageGroup(AgeGroup.TWENTIES)
                .profileImg("http://example.com/img.jpg")
                .createDate(new Timestamp(System.currentTimeMillis()))
                .isPhoneVerified(true)
                .hostedMatches(new ArrayList<>())
                .applies(new ArrayList<>())
                .notifications(new ArrayList<>())
                .build();
    }

    private MatchingDetailDto makeMatchingDetailDto(){
        return MatchingDetailDto.builder()
                .id(1L)
                .title("제목")
                .content("본문")
                .location("장소")
                .locationImg("구장 이미지 주소")
                .date(LocalDate.now())
                .startTime(LocalTime.of(10,0))
                .endTime(LocalTime.of(12, 0))
                .recruitNum(5)
                .cost(5000)
                .isReserved(false)
                .ntrp("Developer")
                .ageGroup("TWENTIES")
                .recruitStatus(RecruitStatus.OPEN)
                .matchingType(MatchingType.SINGLE)
                .applyNum(2)
                .createTime(LocalDateTime.now())
                .build();
    }

    private MatchingPreviewDto makeMatchingPreviewDto(){
        return MatchingPreviewDto.builder()
                .isReserved(true)
                .matchingType(MatchingType.SINGLE)
                .ntrp("ntrp")
                .title("제목")
                .matchingStartDateTime("시작 날짜")
                .build();
    }

    private Matching makeMatching(SiteUser siteUser) {
        return Matching.builder()
                .id(1L)
                .siteUser(siteUser)
                .title("테니스 매칭")
                .content("주말에 함께 테니스 칠 분 구합니다.")
                .location("서울 강남구 역삼동")
                .locationImg("http://example.com/locationImg.jpg")
                .date(new Date(2023, 12, 03))
                .startTime(Time.valueOf("10:00:00"))
                .endTime(Time.valueOf("12:00:00"))
                .recruitNum(4)
                .cost(20000)
                .isReserved(false)
                .ntrp("3.0 ~ 3.5")
                .age("20 ~ 30")
                .recruitStatus(RecruitStatus.OPEN)
                .matchingType(MatchingType.SINGLE)
                .applyNum(0)
                .createTime(new Timestamp(System.currentTimeMillis()))
                .confirms(new ArrayList<>())
                .build();
    }
}