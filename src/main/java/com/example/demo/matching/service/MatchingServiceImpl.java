package com.example.demo.matching.service;

import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.entity.Matching;
import com.example.demo.matching.dto.ApplyListResponseDto;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.matching.repository.MatchingRepository;
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
    public Map<String, String> getApplyList(long userId, long matchingId) throws JsonProcessingException {
        var matching = matchingRepository.findById(matchingId).get();
        var recruitNum = matching.getRecruitNum();
        var confirmedNum = matching.getConfirmedNum();
        var applyNum = applyRepository.countByMatching_IdAndStatus(matchingId, ApplyStatus.PENDING).get();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = new HashMap<>();

        String recruitNumber = objectMapper.writeValueAsString(recruitNum);
        String confirmedNumber = objectMapper.writeValueAsString(confirmedNum);
        String applyNumber = objectMapper.writeValueAsString(applyNum);

        if (isOrganizer(userId, matching)) {
            var resultsForAdmin = applyRepository.findByMatching_Id(matchingId)
                    .get().stream().map((apply)
                            -> ApplyListResponseDto.builder()
                            .applyId(apply.getId())
                            .siteUserId(apply.getSiteUser().getId())
                            .nickname(apply.getSiteUser().getNickname())
                            .applyStatus(apply.getStatus())
                            .build()).collect(Collectors.toList());

                result.put("applyListForAdmin", objectMapper.writeValueAsString(resultsForAdmin));
                result.put("recruitNum", recruitNumber);
                result.put("confirmedNum", confirmedNumber);
                result.put("applyNum", applyNumber);

                return result;
            }

        var applyListForUser = applyRepository.findByMatching_IdAndStatus(matchingId, ApplyStatus.ACCEPTED)
                .get().stream().map((apply)
                        -> ApplyListResponseDto.builder()
                        .applyId(apply.getId())
                        .siteUserId(apply.getSiteUser().getId())
                        .nickname(apply.getSiteUser().getNickname())
                        .build()).collect(Collectors.toList());

            result.put("applyListForUser", objectMapper.writeValueAsString(applyListForUser));
            result.put("recruitNum", recruitNumber);
            result.put("confirmedNum", confirmedNumber);

            return result;
        }

    private static boolean isOrganizer(long userId, Matching matching) {
        return matching.getSiteUser().getId() == userId;
    }
}