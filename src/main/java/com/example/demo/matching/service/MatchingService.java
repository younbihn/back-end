package com.example.demo.matching.service;

import com.example.demo.matching.dto.ApplyListResponseDto.ApplyListResponseDtoBuilder;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.response.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface MatchingService {
    MatchingDetailDto create(Long userId, MatchingDetailDto matchingDetailDto);
    MatchingDetailDto update(MatchingDetailDto matchingDetailDto);
    void delete(Long matchingId);
    List<MatchingPreviewDto> getList();
    MatchingDetailDto getDetail(Long matchingId);

    ResponseDto getApplyList(long userId, long matchId) throws JsonProcessingException;
}