package com.example.demo.matching.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressRequestDto {

    private Integer currentPage;
    private Integer countPerPage;
    private String keyword;
    private String resultType;
    private String hstryYn;
    private String firstSort;

    public static AddressRequestDto fromKeyword(String keyword) {
        return AddressRequestDto.builder()
                .currentPage(1)
                .countPerPage(15)
                .keyword(keyword)
                .resultType("json")
                .hstryYn("Y")
                .firstSort("none")
                .build();
    }
}
