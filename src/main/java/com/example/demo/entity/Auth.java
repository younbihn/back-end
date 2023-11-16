package com.example.demo.entity;

import com.example.demo.type.AgeGroup;
import com.example.demo.type.GenderType;
import com.example.demo.type.Ntrp;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Auth {
    @Data
    public static class SignIn {
        private String email;
        private String password;
    }

    @Data
    public static class SignUp {

        private String email;
        private String password;
        private List<String> roles;
        private String nickname;
        private String phoneNumber;
        private GenderType gender;
        private Ntrp ntrp;
        private String address;
        private String zipCode;
        private AgeGroup ageGroup;
        private Timestamp createDate;

        public SiteUser toUser() {
            return SiteUser.builder()
                    .email(this.email)
                    .password(this.password)
                    .roles(this.roles)
                    .nickname(this.nickname)
                    .phoneNumber(this.phoneNumber)
                    .gender(this.gender)
                    .ntrp(this.ntrp)
                    .address(this.address)
                    .zipCode(this.zipCode)
                    .ageGroup(this.ageGroup)
                    .build();
        }
    }
}
