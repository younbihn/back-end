package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "MANNER_SCORE")
public class MannerScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @Column(name = "MATCHING_ID", nullable = false)
    private Matching matching;

    @ManyToOne
    @Column(name = "SITE_USER_ID", nullable = false)
    private SiteUser siteUser;

    @Column(name = "SCORE", nullable = false)
    private Integer score;

    @Column(name = "CREATE_TIME", nullable = false)
    private Timestamp createTime;
}