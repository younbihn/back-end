package com.example.demo.siteuser.dto;

import com.example.demo.entity.Matching;
import com.example.demo.type.MatchingType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchingMyMatchingDto {
    private String title;
    private String location;
    private LocalDate date;
    private MatchingType matchingType;

    public static MatchingMyMatchingDto fromEntity(Matching matching) {
        return MatchingMyMatchingDto.builder()
                .title(matching.getTitle())
                .location(matching.getLocation())
                .date(matching.getDate())
                .matchingType(matching.getMatchingType())
                .build();
    }
}
