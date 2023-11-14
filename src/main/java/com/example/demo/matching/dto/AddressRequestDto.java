package com.example.demo.matching.dto;

import lombok.Builder;
import lombok.Data;
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

    public static AddressRequestDto fromKeyword(KeywordDto keywordDto) {
        return AddressRequestDto.builder()
                .currentPage(1)
                .countPerPage(15)
                .keyword(keywordDto.getKeyword())
                .resultType("json")
                .hstryYn("Y")
                .firstSort("none")
                .build();
    }
}
