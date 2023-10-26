package com.example.demo.entity;

import com.example.demo.code.ApplyStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "APPLY")
public class Apply {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 50, nullable = false, columnDefinition = "DEFAULT 'PENDING'")
    private ApplyStatus status;
}