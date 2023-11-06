package com.example.demo.matching.dto;

import com.example.demo.entity.Matching;
import com.example.demo.type.MatchingType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchingMyHostedDto {
    private String title;
    private String location;
    private LocalDate date;
    private MatchingType matchingType;

    public static MatchingMyHostedDto fromEntity(Matching matching) {
        return MatchingMyHostedDto.builder()
                .title(matching.getTitle())
                .location(matching.getLocation())
                .date(matching.getDate().toLocalDate())
                .matchingType(matching.getMatchingType())
                .build();
    }
}
