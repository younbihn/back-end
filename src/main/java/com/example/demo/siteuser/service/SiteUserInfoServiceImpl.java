package com.example.demo.siteuser.service;

import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import com.example.demo.siteuser.dto.MatchingMyMatchingDto;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.siteuser.dto.SiteUserInfoDto;
import com.example.demo.siteuser.dto.SiteUserMyInfoDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteUserInfoServiceImpl implements SiteUserInfoService {
    private final SiteUserRepository siteUserRepository;
    private final MatchingRepository matchingRepository;
    private final ApplyRepository applyRepository;

    @Autowired
    public SiteUserInfoServiceImpl(SiteUserRepository siteUserRepository, MatchingRepository matchingRepository, ApplyRepository applyRepository) {
        this.siteUserRepository = siteUserRepository;
        this.matchingRepository = matchingRepository;
        this.applyRepository = applyRepository;
    }

    @Override
    public SiteUserInfoDto getSiteUserInfoById(Long userId) {
        SiteUser siteUser = siteUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        return SiteUserInfoDto.fromEntity(siteUser);
    }

    @Override
    public SiteUserMyInfoDto getSiteUserMyInfoById(Long userId) {
        SiteUser siteUser = siteUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        return SiteUserMyInfoDto.fromEntity(siteUser);
    }

    @Override
    public List<MatchingMyMatchingDto> getMatchingBySiteUser(SiteUser siteUser) {
        List<Matching> matchingList = matchingRepository.findMatchingBySiteUser(siteUser);

        if (matchingList != null && !matchingList.isEmpty()) {
            return matchingList.stream()
                    .map(MatchingMyMatchingDto::fromEntity)
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("No matching data found for user with ID: " + siteUser.getId());
        }
    }

    @Override
    public List<MatchingMyMatchingDto> getApplyBySiteUser(SiteUser siteUser) {
        List<Apply> applyList = applyRepository.findApplyBySiteUser(siteUser);

        if (applyList != null && !applyList.isEmpty()) {
            List<MatchingMyMatchingDto> matchingDtos = applyList.stream()
                    .filter(apply -> apply.getMatching() != null)
                    .map(apply -> MatchingMyMatchingDto.fromEntity(apply.getMatching()))
                    .collect(Collectors.toList());
            return matchingDtos;
        } else {
            throw new EntityNotFoundException("No matching data found for user with ID: " + siteUser.getId());
        }
    }
}