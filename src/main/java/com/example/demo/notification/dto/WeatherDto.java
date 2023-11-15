package com.example.demo.notification.dto;

import com.example.demo.type.PrecipitationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherDto {

    private String precipitationProbability;
    private PrecipitationType precipitationType;
}
