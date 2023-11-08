package com.example.demo.siteuser.controller;

import com.example.demo.siteuser.dto.SiteUserInfoDto;
import com.example.demo.siteuser.dto.SiteUserMyInfoDto;
import com.example.demo.siteuser.service.SiteUserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SiteUserInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SiteUserInfoService siteUserInfoService;


    @InjectMocks
    private SiteUserInfoController siteUserInfoController;

    @BeforeEach
    public void setup() {
        siteUserInfoService = Mockito.mock(SiteUserInfoService.class);
        siteUserInfoController = new SiteUserInfoController(siteUserInfoService);
        mockMvc = MockMvcBuilders.standaloneSetup(siteUserInfoController).build();
    }

    @Test
    public void testGetSiteUserInfoById() throws Exception {
        SiteUserInfoDto mockDto = SiteUserInfoDto.builder()
                        .nickname("test")
                        .build();
        when(siteUserInfoService.getSiteUserInfoById(any(Long.class))).thenReturn(mockDto);

        mockMvc.perform(get("/api/users/profile/{siteUser}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nickname").value("test")); // replace field and expected value accordingly
    }

    @Test
    public void testGetSiteUserMyInfoById() throws Exception {
        SiteUserMyInfoDto mockDto = SiteUserMyInfoDto.builder()
                .nickname("test")
                .build();
        when(siteUserInfoService.getSiteUserMyInfoById(any(Long.class))).thenReturn(mockDto);

        mockMvc.perform(get("/api/users/my-page/{siteUser}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nickname").value("test")); // replace field and expected value accordingly
    }

    // 객체 불일치로 코드 수정 필요
//    @Test
//    public void testGetMatchingBySiteUser() throws Exception {
//        SiteUser siteUser = SiteUser.builder()
//                .id(1L)
//                .build();
//        MatchingMyMatchingDto dto1 = MatchingMyMatchingDto.builder()
//                .title("testTitle1")
//                .location("testLocation1")
//                .date(LocalDate.of(2022, 10, 1))
//                .matchingType(MatchingType.SINGLE) // 예시 타입
//                .build();
//        MatchingMyMatchingDto dto2 = MatchingMyMatchingDto.builder()
//                .title("testTitle2")
//                .location("testLocation2")
//                .date(LocalDate.of(2022, 10, 1))
//                .matchingType(MatchingType.SINGLE) // 예시 타입
//                .build();
//
//        List<MatchingMyMatchingDto> mockList = Arrays.asList(dto1, dto2);
//        when(siteUserInfoService.getMatchingBySiteUser(any(SiteUser.class))).thenReturn(mockList);
//
//        mockMvc.perform(get("/api/users/my-page/hosted/{siteUserId}", siteUser)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].title").value("testTitle1"));
//    }
//
//    @Test
//    public void testGetApplyBySiteUser() throws Exception {
//        List<MatchingMyMatchingDto> mockList = Arrays.asList(new MatchingMyMatchingDto()); // populate with test data
//        when(siteUserInfoService.getApplyBySiteUser(any(SiteUser.class))).thenReturn(mockList);
//
//        mockMvc.perform(get("/api/users/my-page/apply/{siteUser}", 1L)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].field").value("expected value")); // replace field and expected value accordingly
//    }
}
