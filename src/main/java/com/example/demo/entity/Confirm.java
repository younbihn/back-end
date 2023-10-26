package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
public class Confirm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(name = "MATCHING_ID", nullable = false)
    private Matching matching;

    @ManyToOne
    @Column(name = "SITE_USER_ID", nullable = false)
    private SiteUser siteUser;

    @Column(name = "CREATE_TIME", nullable = false)
    private Timestamp createTime;
}