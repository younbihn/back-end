package com.example.demo.siteuser.service;

import com.example.demo.entity.SiteUser;
import com.example.demo.siteuser.dto.MatchingMyHostedDto;
import com.example.demo.siteuser.dto.SiteUserInfoDto;
import com.example.demo.siteuser.dto.SiteUserMyInfoDto;

import java.util.List;


public interface SiteUserInfoService {
    SiteUserInfoDto getSiteUserInfoById(Long userId);
    SiteUserMyInfoDto getSiteUserMyInfoById(Long userId);
    List<MatchingMyHostedDto> getMatchingBySiteUser(SiteUser siteUser);
    List<MatchingMyHostedDto> getApplyBySiteUser(SiteUser siteUser);
}