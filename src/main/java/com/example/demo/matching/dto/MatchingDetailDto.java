package com.example.demo.matching.dto;

import com.example.demo.entity.Matching;
import com.example.demo.type.AgeGroup;
import com.example.demo.type.MatchingType;
import com.example.demo.type.Ntrp;
import com.example.demo.type.RecruitStatus;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchingDetailDto {
    private long id;
    private long creatorUserId;
    private String title;
    private String content;
    private String location;
    private double lat;
    private double lon;
    private String locationImg;
    private String date;
    private String startTime;
    private String endTime;
    private String recruitDueDate;
    private int recruitNum;
    private int cost;
    private boolean isReserved;
    private Ntrp ntrp;
    private AgeGroup ageGroup;
    private RecruitStatus recruitStatus;
    private MatchingType matchingType;
    private int confirmedNum;
    private String createTime;

    public static MatchingDetailDto fromEntity(Matching matching) {
        return MatchingDetailDto.builder()
                .id(matching.getId())
                .creatorUserId(matching.getSiteUser().getId())
                .title(matching.getTitle())
                .content(matching.getContent())
                .location(matching.getLocation())
                .lat(matching.getLat())
                .lon(matching.getLon())
                .locationImg(matching.getLocationImg())
                .date(matching.getDate().toString())
                .startTime(matching.getStartTime().toString())
                .endTime(matching.getEndTime().toString())
                .recruitDueDate(matching.getRecruitDueDate().toString())
                .recruitNum(matching.getRecruitNum())
                .confirmedNum(matching.getConfirmedNum())
                .cost(matching.getCost())
                .isReserved(matching.getIsReserved())
                .ntrp(matching.getNtrp())
                .ageGroup(matching.getAge())
                .recruitStatus(matching.getRecruitStatus())
                .matchingType(matching.getMatchingType())
                .createTime(matching.getCreateTime().toString())
                .build();
    }
}