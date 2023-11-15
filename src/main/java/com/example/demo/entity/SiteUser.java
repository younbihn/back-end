package com.example.demo.entity;

import com.example.demo.type.AgeGroup;
import com.example.demo.type.GenderType;
import com.example.demo.type.Ntrp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "SITE_USER")
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PASSWORD", length = 1023, nullable = false, columnDefinition = "VARCHAR(1023)")
    private String password;

    @Column(name = "NICKNAME", length = 50, nullable = false, columnDefinition = "VARCHAR(50)")
    private String nickname;

    @Column(name = "EMAIL", length = 255, nullable = false, columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(name = "PHONE_NUMBER", length = 50, nullable = false, columnDefinition = "VARCHAR(50)")
    private String phoneNumber;

    @Column(name = "MANNER_SCORE")
    private Integer mannerScore;

    @Column(name = "PENALTY_SCORE")
    private Integer penaltyScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", length = 50, nullable = false, columnDefinition = "VARCHAR(50)")
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "NTRP")
    private Ntrp ntrp;

    @Column(name = "ADDRESS", length = 50, nullable = false, columnDefinition = "VARCHAR(255)")
    private String address;

    @Column(name = "ZIP_CODE", length = 50, nullable = false, columnDefinition = "VARCHAR(50)")
    private String zipCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "AGE_GROUP", length = 50, nullable = false, columnDefinition = "VARCHAR(50)")
    private AgeGroup ageGroup;

    @Column(name = "PROFILE_IMG", length = 1023, columnDefinition = "VARCHAR(1023)")
    private String profileImg;

    @Column(name = "CREATE_DATE", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createDate;

    @Column(name = "IS_PHONE_VERIFIED", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isPhoneVerified;

    @OneToMany(mappedBy = "siteUser")
    private List<Matching> hostedMatches; // 주최한 매칭

    @OneToMany(mappedBy = "siteUser")
    private List<Apply> applies; // 신청 내역

    @OneToMany(mappedBy = "siteUser")
    private List<Notification> notifications; // 알림

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setNtrp(Ntrp ntrp) {
        this.ntrp = ntrp;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
