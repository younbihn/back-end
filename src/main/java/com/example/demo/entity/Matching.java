package com.example.demo.entity;

import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.type.MatchingType;
import com.example.demo.type.RecruitStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SITE_USER_ID", nullable = false)
    private SiteUser siteUser;

    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;

    @Column(name = "CONTENT", length = 1023)
    private String content;

    @Column(name = "LOCATION", length = 255, nullable = false)
    private String location;

    @Column(name = "LOCATION_IMG", length = 1023)
    private String locationImg;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "START_TIME", nullable = false)
    private Time startTime;

    @Column(name = "END_TIME", nullable = false)
    private Time endTime;

    @Column(name = "RECRUIT_DUE_DATE", nullable = false)
    private Timestamp recruitDueDate;

    @Column(name = "RECRUIT_NUM", nullable = false)
    private Integer recruitNum;

    @Column(name = "COST", nullable = false)
    private Integer cost;

    @Column(name = "IS_RESERVED", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isReserved;

    @Column(name = "NTRP", length = 50)
    private String ntrp; // 사용자가 범위를 입력할 수 있으므로 string

    @Column(name = "AGE", length = 50)
    private String age; // 사용자가 범위를 입력할 수 있으므로 string

    @Column(name = "RECRUIT_STATUS", length = 50)
    private RecruitStatus recruitStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "MATCHING_TYPE", length = 50)
    private MatchingType matchingType;

    @Column(name = "APPLY_NUM", columnDefinition = "INT DEFAULT 0")
    private Integer applyNum;

    @Column(name = "CREATE_TIME", nullable = false)
    private Timestamp createTime;

    @OneToMany(mappedBy = "matching")
    private List<Confirm> confirms; // 확정 인원 목록 - 채팅방 만들 때 사용

    public static Matching fromDto(MatchingDetailDto matchingDetailDto, SiteUser siteUser) {
        return Matching.builder()
                .siteUser(siteUser)
                .title(matchingDetailDto.getTitle())
                .content(matchingDetailDto.getContent())
                .location(matchingDetailDto.getLocation())
                .locationImg(matchingDetailDto.getLocationImg()) // TODO: S3 연동
                .date(Date.valueOf(matchingDetailDto.getDate()))
                .startTime(Time.valueOf(matchingDetailDto.getStartTime()))
                .endTime(Time.valueOf(matchingDetailDto.getEndTime()))
                .recruitNum(matchingDetailDto.getRecruitNum())
                .cost(matchingDetailDto.getCost())
                .isReserved(matchingDetailDto.getIsReserved())
                .ntrp(matchingDetailDto.getNtrp())
                .age(matchingDetailDto.getAgeGroup())
                .recruitStatus(matchingDetailDto.getRecruitStatus())
                .matchingType(matchingDetailDto.getMatchingType())
                .applyNum(matchingDetailDto.getApplyNum())
                .createTime(Timestamp.valueOf(matchingDetailDto.getCreateTime()))
                .build();
    }
}