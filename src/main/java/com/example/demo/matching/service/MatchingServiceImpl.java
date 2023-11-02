package com.example.demo.matching.service;

import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.exception.impl.JsonException;
import com.example.demo.matching.dto.ApplyListResponseDto;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.response.ResponseDto;
import com.example.demo.response.ResponseUtil;
import com.example.demo.type.ApplyStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseDto getApplyList(long userId, long matchingId) throws JsonProcessingException {
        var matching = matchingRepository.findById(matchingId).get();
        var recruitNum = matching.getRecruitNum();
        var confirmedNum = matching.getConfirmedNum();
        var applyNum = applyRepository.countByMatching_IdAndStatus(matchingId, ApplyStatus.PENDING).get();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> response = new HashMap<>();

        String recruitNumToString = objectMapper.writeValueAsString(recruitNum);
        String confirmedNumToString = objectMapper.writeValueAsString(confirmedNum);
        String applyNumToString = objectMapper.writeValueAsString(applyNum);

        if (matching.getSiteUser().getId() == userId) {

            var applyListForAdmin = applyRepository.findByMatching_Id(matchingId)
                    .get().stream().map((apply)
                            -> ApplyListResponseDto.builder()
                            .applyId(apply.getId())
                            .siteUserId(apply.getSiteUser().getId())
                            .nickname(apply.getSiteUser().getNickname())
                            .applyStatus(apply.getStatus())
                            .build()).collect(Collectors.toList());

                String applyListToStringForAdmin = objectMapper.writeValueAsString(applyListForAdmin);

                response.put("applyListForAdmin", applyListToStringForAdmin);
                response.put("recruitNum", recruitNumToString);
                response.put("confirmedNum", confirmedNumToString);
                response.put("applyNum", applyNumToString);

                return ResponseUtil.SUCCESS("관리자 매칭 신청 내역을 불러왔습니다.", response);
            }

        var applyListForUser = applyRepository.findByMatching_IdAndStatus(matchingId, ApplyStatus.ACCEPTED)
                .get().stream().map((apply)
                        -> ApplyListResponseDto.builder()
                        .applyId(apply.getId())
                        .siteUserId(apply.getSiteUser().getId())
                        .nickname(apply.getSiteUser().getNickname())
                        .build()).collect(Collectors.toList());

            String applyListToStringForUser = objectMapper.writeValueAsString(applyListForUser);

            response.put("applyListForAdmin", applyListToStringForUser);
            response.put("recruitNum", recruitNumToString);
            response.put("confirmedNum", confirmedNumToString);

            return ResponseUtil.SUCCESS("사용자 매칭 신청 내역을 불러왔습니다.", response);
        }
}