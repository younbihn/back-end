package com.example.demo.matching.service;

import com.example.demo.entity.Matching;
import com.example.demo.matching.dto.ApplyContents;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchingService {
    Matching create(Long userId, MatchingDetailDto matchingDetailDto);
    Matching update(Long userId, Long matchingId, MatchingDetailDto matchingDetailDto);
    void delete(Long userId, Long matchingId);
    Page<MatchingPreviewDto> getList(Pageable pageable);

    Page<MatchingPreviewDto> getListByDistance(Long userId, Pageable pageable);

    MatchingDetailDto getDetail(Long matchingId);
    ApplyContents getApplyContents(long userId, long matchId) throws JsonProcessingException;
}