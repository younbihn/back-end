package com.example.demo.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "REVIEW")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MATCHING_ID", nullable = false)
    private Matching matching;

    @ManyToOne
    @JoinColumn(name = "OBJECT_USER_ID", nullable = false)
    private SiteUser objectUser;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_USER_ID", nullable = false)
    private SiteUser subjectUser;

    @Column(name = "SCORE", nullable = false)
    private Integer score;

    @Column(name = "CREATE_TIME", nullable = false)
    private Timestamp createTime;

    @Column(name = "POSITIVE_SCORE", nullable = false)
    private Integer positiveScore;

    @Column(name = "NEGATIVE_SCORE", nullable = false)
    private Integer negativeScore;
}