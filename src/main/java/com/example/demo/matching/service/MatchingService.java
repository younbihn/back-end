package com.example.demo.matching.service;

import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchingService {
    public MatchingDetailDto create(Long userId, MatchingDetailDto matchingDetailDto);
    public MatchingDetailDto update(Long userId, Long matchingId, MatchingDetailDto matchingDetailDto);
    public long delete(Long userId, Long matchingId);
    public Page<MatchingPreviewDto> getList(Pageable pageable);
    public MatchingDetailDto getDetail(Long matchingId);
}