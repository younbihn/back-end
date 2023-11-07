package com.example.demo.matching.service;

import com.example.demo.matching.dto.ApplyContents;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchingService {
    public void create(Long userId, MatchingDetailDto matchingDetailDto);
    public void update(Long userId, Long matchingId, MatchingDetailDto matchingDetailDto);
    public void delete(Long userId, Long matchingId);
    public Page<MatchingPreviewDto> getList(Pageable pageable);
    public MatchingDetailDto getDetail(Long matchingId);
    public ApplyContents getApplyContents(long userId, long matchId) throws JsonProcessingException;
}