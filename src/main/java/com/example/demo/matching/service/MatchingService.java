package com.example.demo.matching.service;

import com.example.demo.entity.Matching;
import com.example.demo.matching.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchingService {
    Matching create(Long userId, MatchingDetailDto matchingDetailDto);
    Matching update(Long userId, Long matchingId, MatchingDetailDto matchingDetailDto);
    void delete(Long userId, Long matchingId);
    Page<MatchingPreviewDto> findFilteredMatching(FilterRequestDto filterRequestDto, Pageable pageable);
    Page<MatchingPreviewDto> findCloseMatching(LocationDto locationDto, Double distance, Pageable pageable);
    MatchingDetailDto getDetail(Long matchingId);
    ApplyContents getApplyContents(String email, long matchId) throws JsonProcessingException;
}