package com.example.demo.siteuser.controller;

import com.example.demo.siteuser.dto.MatchingMyMatchingDto;
import com.example.demo.siteuser.dto.SiteUserInfoDto;
import com.example.demo.siteuser.dto.SiteUserMyInfoDto;
import com.example.demo.siteuser.service.SiteUserInfoService;
import com.example.demo.type.MatchingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Test
    public void testGetMatchingBySiteUser() throws Exception {
        MatchingMyMatchingDto dto = MatchingMyMatchingDto.builder()
                .title("testTitle1")
                .location("testLocation1")
                .date(LocalDate.of(2022, 10, 1))
                .matchingType(MatchingType.SINGLE)
                .build();
        List<MatchingMyMatchingDto> mockList = Arrays.asList(dto);

        // Mocking service method
        when(siteUserInfoService.getMatchingBySiteUser(anyLong())).thenReturn(mockList);

        // Act: Perform the actual request
        mockMvc.perform(get("/api/users/my-page/hosted/1")
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert: Verify the outcomes
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("testTitle1"));
    }

    @Test
    public void testGetApplyBySiteUser() throws Exception {
        MatchingMyMatchingDto dto = MatchingMyMatchingDto.builder()
                .title("testTitle1")
                .location("testLocation1")
                .date(LocalDate.of(2022, 10, 1))
                .matchingType(MatchingType.SINGLE)
                .build();
        List<MatchingMyMatchingDto> mockList = Arrays.asList(dto);
        when(siteUserInfoService.getApplyBySiteUser(anyLong())).thenReturn(mockList);

        mockMvc.perform(get("/api/users/my-page/apply/{siteUser}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("testTitle1"));
    }
}
