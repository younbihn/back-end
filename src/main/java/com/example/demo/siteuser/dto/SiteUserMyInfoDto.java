package com.example.demo.siteuser.dto;

import com.example.demo.entity.SiteUser;
import com.example.demo.type.AgeGroup;
import com.example.demo.type.GenderType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteUserMyInfoDto {
    private Long id;
    private String nickname;
    private String email;
    private String phoneNumber;
    private Integer mannerScore;
    private Integer penaltyScore;
    private GenderType gender;
    private BigDecimal ntrp;
    private String locationSi;
    private String locationGu;
    private AgeGroup ageGroup;
    private String profileImg;

    public static SiteUserMyInfoDto fromEntity(SiteUser siteUser) {
        return SiteUserMyInfoDto.builder()
                .id(siteUser.getId())
                .nickname(siteUser.getNickname())
                .email(siteUser.getEmail())
                .phoneNumber(siteUser.getPhoneNumber())
                .mannerScore(siteUser.getMannerScore())
                .penaltyScore(siteUser.getPenaltyScore())
                .gender(siteUser.getGender())
                .ntrp(siteUser.getNtrp())
                .locationSi(siteUser.getLocationSi())
                .locationGu(siteUser.getLocationGu())
                .ageGroup(siteUser.getAgeGroup())
                .profileImg(siteUser.getProfileImg())
                .build();
    }
}