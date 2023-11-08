package com.example.demo.siteuser.service;

import com.example.demo.entity.SiteUser;
import com.example.demo.siteuser.dto.MatchingMyMatchingDto;
import com.example.demo.siteuser.dto.SiteUserInfoDto;
import com.example.demo.siteuser.dto.SiteUserMyInfoDto;

import java.util.List;


public interface SiteUserInfoService {
    SiteUserInfoDto getSiteUserInfoById(Long userId);
    SiteUserMyInfoDto getSiteUserMyInfoById(Long userId);
    List<MatchingMyMatchingDto> getMatchingBySiteUser(SiteUser siteUser);
    List<MatchingMyMatchingDto> getApplyBySiteUser(SiteUser siteUser);
}