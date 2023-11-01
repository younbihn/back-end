package com.example.demo.matching.service;

import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.matching.dto.ApplyListResponseDto;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.response.ResponseDto;
import com.example.demo.response.ResponseUtil;
import com.example.demo.type.ApplyStatus;
import com.example.demo.type.RecruitStatus;
import java.awt.CardLayout;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingServiceImpl implements MatchingService {

    private final MatchingRepository matchingRepository;
    private final ApplyRepository applyRepository;

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
    public ResponseDto<List<ApplyListResponseDto>> getApplyList(Long userId, long matchingId) {
        var matching = matchingRepository.findById(matchingId).get();
        var recruitNum = matching.getRecruitNum();
        var confirmedNum = matching.getConfirmedNum();
        var applyNum = applyRepository.countByMatching_IdAndStatus(matchingId, ApplyStatus.PENDING).get();

        if (matching.getSiteUser().getId() == userId) {
            var applyListForAdmin = applyRepository.findByMatching_Id(matchingId).get()
                    .stream().map((apply)
                            -> ApplyListResponseDto.builder()
                            .applyId(apply.getId())
                            .siteUserId(apply.getSiteUser().getId())
                            .nickname(apply.getSiteUser().getNickname())
                            .recruitNum(recruitNum)
                            .confirmNum(confirmedNum)
                            .applyNum(applyNum)
                            .build()).collect(Collectors.toList());
            return ResponseUtil.SUCCESS("매칭 신청 내역을 불러왔습니다.", applyListForAdmin);
        }
        var applyListForUser = applyRepository.findByMatching_IdAndStatus(matchingId, ApplyStatus.ACCEPTED).get()
                .stream().map((apply)
                        -> ApplyListResponseDto.builder()
                        .applyId(apply.getId())
                        .siteUserId(apply.getSiteUser().getId())
                        .nickname(apply.getSiteUser().getNickname())
                        .recruitNum(recruitNum)
                        .confirmNum(confirmedNum)
                        .build()).collect(Collectors.toList());
        return ResponseUtil.SUCCESS("매칭 신청 내역을 불러왔습니다.", applyListForUser);
    }

}