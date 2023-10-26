package com.example.demo.entity;

import com.example.demo.code.MatchingType;
import com.example.demo.code.RecruitStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


@Entity
@Table(name = "MATCHING")
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(name = "SITE_USER_ID", nullable = false)
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

    @Column(name = "RECRUIT_NUM", nullable = false)
    private Integer recruitNum;

    @Column(name = "COST", nullable = false)
    private Integer cost;

    @Column(name = "RESERVATION_STATUS", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean reservationStatus;

    @Column(name = "NTRP", length = 50)
    private String ntrp; // 사용자가 범위를 입력할 수 있으므로 string

    @Column(name = "AGE", length = 50)
    private String age; // 사용자가 범위를 입력할 수 있으므로 string

    @Column(name = "RECRUIT_STATUS", length = 50)
    private RecruitStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "MATCHING_TYPE", length = 50)
    private MatchingType type;

    @Column(name = "APPLY_NUM", columnDefinition = "INT DEFAULT 0")
    private Integer applyNum;

    @Column(name = "CREATE_TIME", nullable = false)
    private Timestamp createTime;
}