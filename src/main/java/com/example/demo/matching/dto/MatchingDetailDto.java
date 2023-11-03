package com.example.demo.matching.dto;

import com.example.demo.entity.Matching;
import com.example.demo.type.MatchingType;
import com.example.demo.type.RecruitStatus;
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
    private Long id;
    private Long creatorUserId;
    private String title;
    private String content;
    private String location;
    private String locationImg;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime recruitDueDate;
    private Integer recruitNum;
    private Integer cost;
    private Boolean isReserved;
    private String ntrp;
    private String ageGroup;
    private RecruitStatus recruitStatus;
    private MatchingType matchingType;
    private Integer applyNum;
    private LocalDateTime createTime;

    public static MatchingDetailDto fromEntity(Matching matching) {
        return MatchingDetailDto.builder()
                .id(matching.getId())
                .creatorUserId(matching.getSiteUser().getId())
                .title(matching.getTitle())
                .content(matching.getContent())
                .location(matching.getLocation())
                .locationImg(matching.getLocationImg())
                .date(matching.getDate().toLocalDate())
                .startTime(matching.getStartTime().toLocalTime())
                .endTime(matching.getEndTime().toLocalTime())
                .recruitDueDate(matching.getRecruitDueDate().toLocalDateTime())
                .recruitNum(matching.getRecruitNum())
                .cost(matching.getCost())
                .isReserved(matching.getIsReserved())
                .ntrp(matching.getNtrp())
                .ageGroup(matching.getAge())
                .recruitStatus(matching.getRecruitStatus())
                .matchingType(matching.getMatchingType())
                .applyNum(matching.getApplyNum())
                .createTime(matching.getCreateTime().toLocalDateTime())
                .build();
    }
}