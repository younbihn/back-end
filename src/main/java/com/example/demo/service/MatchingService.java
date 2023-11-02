package com.example.demo.service;

import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import java.util.List;

public interface MatchingService {
    public MatchingDetailDto create(MatchingDetailDto matchingDetailDto);
    public MatchingDetailDto update(MatchingDetailDto matchingDetailDto);
    public void delete(Long matchingId);
    public List<MatchingPreviewDto> getList();
    public MatchingDetailDto getDetail(Long matchingId);
}
