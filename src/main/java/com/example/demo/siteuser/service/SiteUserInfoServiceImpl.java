package com.example.demo.siteuser.service;

import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.entity.*;
import com.example.demo.notification.repository.NotificationRepository;
import com.example.demo.siteuser.dto.*;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.siteuser.repository.PenaltyScoreRepository;
import com.example.demo.siteuser.repository.ReportUserRepository;
import com.example.demo.siteuser.repository.SiteUserRepository;
import com.example.demo.type.PenaltyCode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteUserInfoServiceImpl implements SiteUserInfoService {
    private final SiteUserRepository siteUserRepository;
    private final MatchingRepository matchingRepository;
    private final ApplyRepository applyRepository;
    private final PenaltyScoreRepository penaltyScoreRepository;
    private final ReportUserRepository reportUserRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public SiteUserInfoServiceImpl(SiteUserRepository siteUserRepository, MatchingRepository matchingRepository, ApplyRepository applyRepository, PenaltyScoreRepository penaltyScoreRepository, ReportUserRepository reportUserRepository, NotificationRepository notificationRepository) {
        this.siteUserRepository = siteUserRepository;
        this.matchingRepository = matchingRepository;
        this.applyRepository = applyRepository;
        this.penaltyScoreRepository = penaltyScoreRepository;
        this.reportUserRepository = reportUserRepository;
        this.notificationRepository = notificationRepository;
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
        List<Apply> applyList = applyRepository.findAllBySiteUser_Id(userId);

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
        if (dto.getAddress() != null) {
            siteUser.setAddress(dto.getAddress());
        }
        if (dto.getZipCode() != null) {
            siteUser.setZipCode(dto.getZipCode());
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

    @Override
    public List<SiteUserNotificationDto> getNotificationBySiteUser(Long userId) {
        List<Notification> notificationList = notificationRepository.findBySiteUser_Id(userId);

        if (notificationList != null && !notificationList.isEmpty()) {
            return notificationList.stream()
                    .map(SiteUserNotificationDto::fromEntity)
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("No notification data found for user with ID: " + userId);
        }
    }

    @Override
    public void deleteNotification(Long userId, Long notificationId) {
        notificationRepository.deleteByIdAndSiteUser_Id(notificationId, userId);
    }

    @Override
    @Transactional
    public void updatePenaltyScore(Long userId, PenaltyCode penaltyCode) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        int penaltyAmount = penaltyCode == PenaltyCode.OFFENSE_CHAT ||
                penaltyCode == PenaltyCode.DELETE_MATCH_EVEN_SOMEONE_APPLIED ? -10 : -20;

        user.setPenaltyScore(user.getPenaltyScore() == null ? penaltyAmount : user.getPenaltyScore() + penaltyAmount);

        PenaltyScore penaltyScore = new PenaltyScore();
        penaltyScore.setSiteUser(user);
        penaltyScore.setCode(penaltyCode);
        penaltyScore.setScore(penaltyAmount);
        penaltyScore.setCreateTime(new Timestamp(System.currentTimeMillis()));

        penaltyScoreRepository.save(penaltyScore);

        siteUserRepository.save(user);
    }

    @Override
    @Transactional
    public void createReportUser(ReportUserDto reportUserDto) {
        SiteUser siteUser = siteUserRepository.findById(reportUserDto.getSiteUser())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + reportUserDto.getSiteUser()));

        ReportUser reportUser = new ReportUser();
        reportUser.setSiteUser(siteUser);
        reportUser.setTitle(reportUserDto.getTitle());
        reportUser.setContent(reportUserDto.getContent());
        reportUser.setCreateTime(LocalDateTime.now());

        reportUserRepository.save(reportUser);
    }

    @Override
    public List<ViewReportsDto> getAllReports() {
        List<ReportUser> reportUsers = reportUserRepository.findAll();
        if (reportUsers.isEmpty()) {
            throw new EntityNotFoundException("No reports found");
        }
        return reportUsers.stream()
                .map(ViewReportsDto::fromEntity)
                .collect(Collectors.toList());
    }
}