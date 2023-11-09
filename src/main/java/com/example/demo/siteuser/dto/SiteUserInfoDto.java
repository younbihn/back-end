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
public class SiteUserInfoDto{
    private String profileImg;
    private String nickname;
    private String locationSi;
    private String locationGu;
    private BigDecimal ntrp;
    private GenderType gender;
    private Integer mannerScore;
    private Integer penaltyScore;
    private AgeGroup ageGroup;

    public static SiteUserInfoDto fromEntity(SiteUser siteUser) {
        return SiteUserInfoDto.builder()
                .profileImg(siteUser.getProfileImg())
                .nickname(siteUser.getNickname())
                .locationSi(siteUser.getLocationSi())
                .locationGu(siteUser.getLocationGu())
                .ntrp(siteUser.getNtrp())
                .gender(siteUser.getGender())
                .mannerScore(siteUser.getMannerScore())
                .penaltyScore(siteUser.getPenaltyScore())
                .ageGroup(siteUser.getAgeGroup())
                .build();
    }
}