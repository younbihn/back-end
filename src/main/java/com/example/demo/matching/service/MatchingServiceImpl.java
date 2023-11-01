package com.example.demo.matching.service;

import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.response.ResponseDto;
import com.example.demo.response.ResponseUtil;
import com.example.demo.type.RecruitStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingServiceImpl implements MatchingService {
    private final MatchingRepository matchingRepository;

    @Override
    public MatchingDetailDto create(Long userId, MatchingDetailDto matchingDetailDto) {

        return null;
    }

    @Override
    public MatchingDetailDto update(MatchingDetailDto matchingDetailDto) {
        return null;
    }

    @Override
    public void delete(Long matchingId) {

    }

    @Override
    public List<MatchingPreviewDto> getList() {
        return null;
    }

    @Override
    public MatchingDetailDto getDetail(Long matchingId) {
        return null;
    }

    @Override
    public ResponseDto<MatchingDetailDto> confirm(long matchId) {

        var matching = matchingRepository.findById(matchId).get();

        if (matching.getApplyNum() < matching.getRecruitNum()) {
            return ResponseUtil.FAILURE("참여 인원이 모집 인원보다 부족합니다. 참여 인원을 추가해 주세요.", null);
        }

        matching.setRecruitStatus(RecruitStatus.CLOSED);

        MatchingDetailDto matchingDetailDto = MatchingDetailDto.fromEntity(matching);

        return ResponseUtil.SUCCESS("매칭 확정에 성공하였습니다.", matchingDetailDto);
    }
}