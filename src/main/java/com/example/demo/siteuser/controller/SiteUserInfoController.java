package com.example.demo.siteuser.controller;

import com.example.demo.entity.SiteUser;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.siteuser.dto.MatchingMyHostedDto;
import com.example.demo.siteuser.dto.SiteUserInfoDto;
import com.example.demo.siteuser.dto.SiteUserMyInfoDto;
import com.example.demo.siteuser.service.SiteUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class SiteUserInfoController {
    private final SiteUserInfoService siteUserInfoService;

    @Autowired
    private SiteUserRepository siteUserRepository;
    @Autowired
    private MatchingRepository matchingRepository;

    @GetMapping("/profile/{siteUser}")
    public ResponseEntity<SiteUserInfoDto> getSiteUserInfo(@PathVariable(value = "siteUser") Long userId) {
        SiteUserInfoDto siteUserInfoDto = siteUserInfoService.getSiteUserInfoById(userId);

        if (siteUserInfoDto != null) {
            return new ResponseEntity<>(siteUserInfoDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/my-page/{siteUser}")
    public ResponseEntity<SiteUserMyInfoDto> getSiteUserMyInfo(@PathVariable(value = "siteUser") Long userId) {
        SiteUserMyInfoDto siteUserMyInfoDto = siteUserInfoService.getSiteUserMyInfoById(userId);

        if (siteUserMyInfoDto != null) {
            return new ResponseEntity<>(siteUserMyInfoDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/my-page/hosted/{siteUser}")
    public ResponseEntity<List<MatchingMyHostedDto>> getBySiteUser(@PathVariable(value = "siteUser") SiteUser siteUser) {
        List<MatchingMyHostedDto> matchingMyHostedDtos = siteUserInfoService.getBySiteUser(siteUser);

        if (!matchingMyHostedDtos.isEmpty()) {
            return new ResponseEntity<>(matchingMyHostedDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}