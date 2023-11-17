package com.example.demo.entity;

import com.example.demo.siteuser.dto.ReportUserDto;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "REPORT_USER")
public class ReportUser {
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

    @CreatedDate
    @Column(name = "CREATE_TIME") // yyyy-MM-dd HH:mm
    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }
    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
