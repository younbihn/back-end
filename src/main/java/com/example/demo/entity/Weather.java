package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Column(name = "MATCHING_ID", nullable = false)
    private Matching matching;

    //TODO : code로 받을지, string으로 받을지 미정
    @Column(name = "WEATHER", length = 255, nullable = false)
    private String weather;

    @Column(name = "TEMPERATURE", precision = 3, scale = 1)
    private BigDecimal temperature; // 형식은 xx.x
}