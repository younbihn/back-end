package com.example.demo.matching.dto;

import com.example.demo.matching.filter.Region;
import com.example.demo.type.AgeGroup;
import com.example.demo.type.MatchingType;
import com.example.demo.type.Ntrp;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterDto {
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private List<Region> regions;
    private List<MatchingType> matchingTypes;
    private List<AgeGroup> ageGroups;
    private List<Ntrp> ntrps;
}