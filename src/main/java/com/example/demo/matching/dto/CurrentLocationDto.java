package com.example.demo.matching.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentLocationDto {
    private String lat;
    private String lon;
}
