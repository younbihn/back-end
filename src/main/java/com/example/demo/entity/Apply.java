package com.example.demo.entity;

import com.example.demo.type.ApplyStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MATCHING_ID", nullable = false)
    private Matching matching;

    @ManyToOne
    @JoinColumn(name = "SITE_USER_ID", nullable = false)
    private SiteUser siteUser;

    @Column(name = "CREATE_TIME", nullable = false)
    private Timestamp createTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 50, nullable = false, columnDefinition = "DEFAULT 'PENDING'")
    private ApplyStatus status;
}