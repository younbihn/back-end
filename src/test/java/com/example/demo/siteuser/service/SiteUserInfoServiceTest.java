package com.example.demo.siteuser.service;


import com.example.demo.entity.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.siteuser.dto.SiteUserInfoDto;
import com.example.demo.siteuser.dto.SiteUserMyInfoDto;
import com.example.demo.siteuser.service.SiteUserInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SiteUserInfoServiceTest {

    @InjectMocks
    private SiteUserInfoServiceImpl siteUserInfoService;

    @Mock
    private SiteUserRepository siteUserRepository;

    @Test
    public void testGetSiteUserInfoByIdWithValidUserId() {
        Long userId = 1L;
        SiteUser siteUser = SiteUser.builder()
                .id(1L)
                .build();
        when(siteUserRepository.findById(userId)).thenReturn(Optional.of(siteUser));

        SiteUserInfoDto result = siteUserInfoService.getSiteUserInfoById(userId);

        // Add assertions to validate the result
    }

    @Test
    public void testGetSiteUserInfoByIdWithInvalidUserId() {
        Long userId = 2L;
        when(siteUserRepository.findById(userId)).thenReturn(Optional.empty());

        // Add assertions to verify the thrown exception
    }

    @Test
    public void testGetSiteUserMyInfoByIdWithValidUserId() {
        Long userId = 1L;
        SiteUser siteUser = SiteUser.builder()
                .id(1L)
                .build();
        when(siteUserRepository.findById(userId)).thenReturn(Optional.of(siteUser));

        SiteUserMyInfoDto result = siteUserInfoService.getSiteUserMyInfoById(userId);

        // Add assertions to validate the result
    }

    @Test
    public void testGetSiteUserMyInfoByIdWithInvalidUserId() {
        Long userId = 2L;
        when(siteUserRepository.findById(userId)).thenReturn(Optional.empty());

        // Add assertions to verify the thrown exception
    }
}

