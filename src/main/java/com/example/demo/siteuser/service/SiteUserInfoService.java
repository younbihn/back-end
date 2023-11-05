package com.example.demo.siteuser.service;

import com.example.demo.siteuser.dto.SiteUserInfoDto;

public interface SiteUserInfoService {
    SiteUserInfoDto getSiteUserInfoById(Long userId);
}