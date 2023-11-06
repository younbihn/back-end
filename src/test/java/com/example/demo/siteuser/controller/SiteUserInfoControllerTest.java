/*
com.example.demo.siteuser.controller;


import com.example.demo.siteuser.dto.SiteUserInfoDto;
import com.example.demo.siteuser.dto.SiteUserMyInfoDto;
import com.example.demo.siteuser.service.SiteUserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class SiteUserInfoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private SiteUserInfoController siteUserInfoController;

    @Mock
    private SiteUserInfoService siteUserInfoService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(siteUserInfoController).build();
    }

    @Test
    public void testGetSiteUserInfoWithValidUserId() {
        Long userId = 1L;
        SiteUserInfoDto siteUserInfoDto = new SiteUserInfoDto(
 create a valid DTO
);
        when(siteUserInfoService.getSiteUserInfoById(userId)).thenReturn(siteUserInfoDto);

        ResponseEntity<SiteUserInfoDto> response = siteUserInfoController.getSiteUserInfo(userId);

        verify(siteUserInfoService).getSiteUserInfoById(userId);
        // Add assertions for response status and content
    }

    @Test
    public void testGetSiteUserInfoWithInvalidUserId() {
        Long userId = 2L;
        when(siteUserInfoService.getSiteUserInfoById(userId)).thenReturn(null);

        ResponseEntity<SiteUserInfoDto> response = siteUserInfoController.getSiteUserInfo(userId);

        verify(siteUserInfoService).getSiteUserInfoById(userId);
        // Add assertions for response status and content
    }

    @Test
    public void testGetSiteUserMyInfoWithValidUserId() {
        Long userId = 1L;
        SiteUserMyInfoDto siteUserMyInfoDto = new SiteUserMyInfoDto(
 create a valid DTO
);
        when(siteUserInfoService.getSiteUserMyInfoById(userId)).thenReturn(siteUserMyInfoDto);

        ResponseEntity<SiteUserMyInfoDto> response = siteUserInfoController.getSiteUserMyInfo(userId);

        verify(siteUserInfoService).getSiteUserMyInfoById(userId);
        // Add assertions for response status and content
    }

    @Test
    public void testGetSiteUserMyInfoWithInvalidUserId() {
        Long userId = 2L;
        when(siteUserInfoService.getSiteUserMyInfoById(userId)).thenReturn(null);

        ResponseEntity<SiteUserMyInfoDto> response = siteUserInfoController.getSiteUserMyInfo(userId);

        verify(siteUserInfoService).getSiteUserMyInfoById(userId);
        // Add assertions for response status and content
    }
}
*/
