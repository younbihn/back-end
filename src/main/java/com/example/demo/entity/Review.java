package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;


@Entity
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
}