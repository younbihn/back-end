package com.example.demo.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherRequestDto {

    private String numOfRows;
    private String pageNo;
    private String dataType;
    private String baseDate;
    private String baseTime;
    private String nx;
    private String ny;
    public static WeatherRequestDto fromLocationAndDate(LocationAndDateFromMatching locationAndDateFromMatching) {
        return WeatherRequestDto.builder()
                .numOfRows("10")
                .pageNo("1")
                .dataType("JSON")
                .baseDate(locationAndDateFromMatching.getBaseDate())
                .baseTime("0500")
                .nx(locationAndDateFromMatching.getNx())
                .ny(locationAndDateFromMatching.getNy())
                .build();
    }
}
