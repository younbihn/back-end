package com.example.demo.entity;

import com.example.demo.code.NotificationCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(name = "SITE_USER_ID", nullable = false)
    private SiteUser siteUser;

    @ManyToOne
    @Column(name = "MATCHING_ID")
    private Matching matching;

    @Enumerated(EnumType.STRING)
    @Column(name = "CODE", length = 50, nullable = false)
    private NotificationCode code;

    @Column(name = "CONTENT", length = 255, nullable = false)
    private String content;

    @Column(name = "CREATE_DATE", columnDefinition = "TIMESTAMP", nullable = false)
    private Timestamp createDate;
}