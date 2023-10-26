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

    @Enumerated(EnumType.ORDINAL) // 알림은 종류가 많아질 수 있으므로 확장성을 위해 int 로 저장
    @Column(name = "CODE", length = 50, nullable = false)
    private NotificationCode notificationCode;

    @Column(name = "CONTENT", length = 255, nullable = false)
    private String content;

    @Column(name = "CREATE_DATE", columnDefinition = "TIMESTAMP", nullable = false)
    private Timestamp createDate;
}