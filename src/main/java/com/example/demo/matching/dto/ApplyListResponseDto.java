package com.example.demo.matching.dto;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyListResponseDto {
    private Long applyId;
    private Long siteUserId;
    private String nickname;
    private Integer recruitNum;
    private Integer applyNum;
    private Integer confirmNum;
}
