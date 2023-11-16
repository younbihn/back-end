package com.example.demo.siteuser.dto;

import com.example.demo.entity.SiteUser;
import com.example.demo.type.AgeGroup;
import com.example.demo.type.GenderType;
import com.example.demo.type.Ntrp;
import java.time.LocalDateTime;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteUserMyInfoDto {
    private Long id;
    private String password;
    private String nickname;
    private String email;
    private String phoneNumber;
    private Integer mannerScore;
    private Integer penaltyScore;
    private GenderType gender;
    private Ntrp ntrp;
    private String address;
    private String zipCode;
    private AgeGroup ageGroup;
    private String profileImg;
    private LocalDateTime createDate;
    private Boolean isPhoneVerified;

    public static SiteUserMyInfoDto fromEntity(SiteUser siteUser) {
        return SiteUserMyInfoDto.builder()
                .id(siteUser.getId())
                .password(siteUser.getPassword())
                .nickname(siteUser.getNickname())
                .email(siteUser.getEmail())
                .phoneNumber(siteUser.getPhoneNumber())
                .mannerScore(siteUser.getMannerScore())
                .penaltyScore(siteUser.getPenaltyScore())
                .gender(siteUser.getGender())
                .ntrp(siteUser.getNtrp())
                .address(siteUser.getAddress())
                .zipCode(siteUser.getZipCode())
                .ageGroup(siteUser.getAgeGroup())
                .profileImg(siteUser.getProfileImg())
                .createDate(siteUser.getCreateDate())
                .isPhoneVerified(siteUser.getIsPhoneVerified())
                .build();
    }
}