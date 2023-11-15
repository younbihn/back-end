package com.example.demo.siteuser.service;

import static com.example.demo.type.AgeGroup.TWENTIES;
import static com.example.demo.type.GenderType.MALE;
import static com.example.demo.type.Ntrp.DEVELOPMENT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.siteuser.dto.MatchingMyMatchingDto;
import com.example.demo.siteuser.dto.SiteUserInfoDto;
import com.example.demo.siteuser.dto.SiteUserModifyDto;
import com.example.demo.siteuser.dto.SiteUserMyInfoDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.example.demo.type.AgeGroup.TWENTIES;
import static com.example.demo.type.GenderType.MALE;
import static com.example.demo.type.Ntrp.DEVELOPMENT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SiteUserInfoServiceTest {

    @Mock
    private SiteUserRepository siteUserRepository;
    @Mock
    private MatchingRepository matchingRepository;
    @Mock
    private ApplyRepository applyRepository;

    @InjectMocks
    private SiteUserInfoServiceImpl siteUserInfoService;

    @Test
    public void getSiteUserInfoByIdTest() {
        SiteUser siteUser = SiteUser.builder()
                .id(1L)
                .email("test@email.com")
                .password("passwordtest")
                .nickname("testNick")
                .phoneNumber("010-1234-5678")
                .mannerScore(10)
                .penaltyScore(0)
                .gender(MALE)
                .address("서울시")
                .zipCode("12345")
                .ageGroup(TWENTIES)
                .profileImg("test.html")
                .ntrp(DEVELOPMENT)
                .build();
        when(siteUserRepository.findById(any())).thenReturn(Optional.of(siteUser));

        SiteUserInfoDto result = siteUserInfoService.getSiteUserInfoById(1L);
        assertNotNull(result);
    }

    @Test
    public void getSiteUserMyInfoByIdTest() {
        SiteUser siteUser = SiteUser.builder()
                .id(1L)
                .email("test@email.com")
                .password("passwordtest")
                .nickname("testNick")
                .phoneNumber("010-1234-5678")
                .mannerScore(10)
                .penaltyScore(0)
                .gender(MALE)
                .address("서울시")
                .zipCode("12345")
                .ageGroup(TWENTIES)
                .profileImg("test.html")
                .ntrp(DEVELOPMENT)
                .build();
        when(siteUserRepository.findById(any())).thenReturn(Optional.of(siteUser));

        SiteUserMyInfoDto result = siteUserInfoService.getSiteUserMyInfoById(1L);
        assertNotNull(result);
    }

    @Test
    public void getMatchingBySiteUserTest() {
        SiteUser siteUser = SiteUser.builder()
                .id(1L)
                .email("test@email.com")
                .password("passwordtest")
                .nickname("testNick")
                .phoneNumber("010-1234-5678")
                .mannerScore(10)
                .penaltyScore(0)
                .gender(MALE)
                .address("서울시")
                .zipCode("12345")
                .ageGroup(TWENTIES)
                .profileImg("test.html")
                .ntrp(DEVELOPMENT)
                .build();

        Matching matching = Matching.builder()
                .siteUser(siteUser)
                .title("testTitle")
                .content("testContent")
                .date(Date.valueOf(LocalDate.of(2022,10,01)).toLocalDate())
                .build();
        List<Matching> matchings = Arrays.asList(matching);
        when(matchingRepository.findBySiteUser_Id(any())).thenReturn(matchings);

        List<MatchingMyMatchingDto> result = siteUserInfoService.getMatchingBySiteUser(siteUser.getId());
        assertFalse(result.isEmpty());
    }

    @Test
    public void getApplyBySiteUserTest() {
        SiteUser siteUser = SiteUser.builder()
                .id(1L)
                .email("test@email.com")
                .password("passwordtest")
                .nickname("testNick")
                .phoneNumber("010-1234-5678")
                .mannerScore(10)
                .penaltyScore(0)
                .gender(MALE)
                .address("서울시")
                .zipCode("12345")
                .ageGroup(TWENTIES)
                .profileImg("test.html")
                .ntrp(DEVELOPMENT)
                .build();

        Matching matching = Matching.builder()
                .siteUser(siteUser)
                .title("testTitle")
                .content("testContent")
                .date(Date.valueOf(LocalDate.of(2022,10,01)).toLocalDate())
                .build();

        Apply apply = Apply.builder()
                .matching(matching)
                .siteUser(siteUser)
                .build();
        List<Apply> applies = Arrays.asList(apply);


        when(applyRepository.findAllBySiteUser_Id(any())).thenReturn(applies);

        List<MatchingMyMatchingDto> result = siteUserInfoService.getApplyBySiteUser(siteUser.getId());
        assertFalse(result.isEmpty());
    }

    @Test
    public void testUpdateProfileImageService() {
        Long userId = 1L;
        String newImageUrl = "http://example.com/new-image.jpg";

        SiteUser siteUser = SiteUser.builder()
                .profileImg("http://example.com/old-image.jpg")
                .build();

        when(siteUserRepository.findById(userId)).thenReturn(Optional.of(siteUser));

        siteUserInfoService.updateProfileImage(userId, newImageUrl);

        assertEquals(newImageUrl, siteUser.getProfileImg());
    }

    @Test
    public void updateSiteUserInfoTest() {
        Long userId = 1L;
        SiteUserModifyDto modifyDto = SiteUserModifyDto.builder()
                .nickname("test")
                .build();

        SiteUser siteUser = new SiteUser();
        when(siteUserRepository.findById(userId)).thenReturn(Optional.of(siteUser));

        siteUserInfoService.updateSiteUserInfo(userId, modifyDto);

        assertEquals(modifyDto.getNickname(), siteUser.getNickname());
    }
}
