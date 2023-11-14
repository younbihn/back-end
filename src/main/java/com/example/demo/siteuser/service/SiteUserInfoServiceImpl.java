package com.example.demo.siteuser.service;

import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import com.example.demo.siteuser.dto.MatchingMyMatchingDto;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.siteuser.dto.SiteUserInfoDto;
import com.example.demo.siteuser.dto.SiteUserModifyDto;
import com.example.demo.siteuser.dto.SiteUserMyInfoDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<MatchingMyMatchingDto> getMatchingBySiteUser(Long userId) {
        List<Matching> matchingList = matchingRepository.findBySiteUser_Id(userId);

        if (matchingList != null && !matchingList.isEmpty()) {
            return matchingList.stream()
                    .map(MatchingMyMatchingDto::fromEntity)
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("No matching data found for user with ID: " + userId);
        }
    }

    @Override
    public List<MatchingMyMatchingDto> getApplyBySiteUser(Long userId) {
        List<Apply> applyList = applyRepository.findBySiteUser_Id(userId);

        if (applyList != null && !applyList.isEmpty()) {
            List<MatchingMyMatchingDto> matchingDtos = applyList.stream()
                    .filter(apply -> apply.getMatching() != null)
                    .map(apply -> MatchingMyMatchingDto.fromEntity(apply.getMatching()))
                    .collect(Collectors.toList());
            return matchingDtos;
        } else {
            throw new EntityNotFoundException("No matching data found for user with ID: " + userId);
        }
    }

    @Transactional
    @Override
    public void updateProfileImage(Long userId, String imageUrl) {
        SiteUser siteUser = siteUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        siteUser.setProfileImg(imageUrl);
        siteUserRepository.save(siteUser);
    }

    @Transactional
    @Override
    public void updateSiteUserInfo(Long userId, SiteUserModifyDto siteUserModifyDto) {
        SiteUser siteUser = siteUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        updateSiteUserFromDto(siteUser, siteUserModifyDto);
        siteUserRepository.save(siteUser);
    }

    private void updateSiteUserFromDto(SiteUser siteUser, SiteUserModifyDto dto) {
        if (dto.getNickname() != null) {
            siteUser.setNickname(dto.getNickname());
        }
        if (dto.getLocationSi() != null) {
            siteUser.setLocationSi(dto.getLocationSi());
        }
        if (dto.getLocationGu() != null) {
            siteUser.setLocationGu(dto.getLocationGu());
        }
        if (dto.getNtrp() != null) {
            siteUser.setNtrp(dto.getNtrp());
        }
        if (dto.getGender() != null) {
            siteUser.setGender(dto.getGender());
        }
        if (dto.getAgeGroup() != null) {
            siteUser.setAgeGroup(dto.getAgeGroup());
        }
        if (dto.getPassword() != null) {
            siteUser.setPassword(dto.getPassword());
        }
        if (dto.getEmail() != null) {
            siteUser.setEmail(dto.getEmail());
        }
        if (dto.getPhoneNumber() != null) {
            siteUser.setPhoneNumber(dto.getPhoneNumber());
        }
    }

    @Override
    public String getProfileUrl(Long userId) {
        SiteUser siteUser = siteUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        return siteUser.getProfileImg();
    }
}