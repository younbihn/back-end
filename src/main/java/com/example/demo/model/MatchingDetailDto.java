package com.example.demo.model;


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
    private String locationImg; // TODO : S3 연결
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer recruitNum;
    private Integer cost;
    private Boolean isReserved;
    private String ntrp;
    private String ageGroup;
    private RecruitStatus recruitStatus;
    private MatchingType matchingType;
    private Integer applyNum;
    private LocalDateTime createTime;
}
