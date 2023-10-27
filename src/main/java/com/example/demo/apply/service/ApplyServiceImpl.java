package com.example.demo.apply.service;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.entity.Apply;
import com.example.demo.exception.impl.AlreadyExistApplyException;
import com.example.demo.exception.impl.ClosedMatchingException;
import com.example.demo.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.type.ApplyStatus;
import com.example.demo.type.RecruitStatus;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplyServiceImpl implements ApplyService {

    private final ApplyRepository applyRepository;
    private final MatchingRepository matchingRepository;
    private final SiteUserRepository siteUserRepository;

    @Override // 매칭 완료 상태인지도 확인!
    public ApplyDto apply(long userId, long matchId) {
        var user = siteUserRepository.findById(userId);
        var matching = matchingRepository.findById(matchId);

        if (matching.get().getRecruitStatus().equals(RecruitStatus.CLOSED)) { // 신청 마감 검사
            throw new ClosedMatchingException();
        }

        boolean exists = applyRepository.existsBySiteUser_IdAndMatching_Id(userId, matchId);
        if (exists) { // 신청 중복 검사
            var existApply = applyRepository.findBySiteUser_IdAndMatching_Id(userId, matchId);
            if (!existApply.get().getStatus().equals(ApplyStatus.CANCELED)) {
                throw new AlreadyExistApplyException();
            }
            existApply.get().setStatus(ApplyStatus.PENDING); // 취소 신청 내역 있을 경우 상태만 변경
        }

        var applyDto = ApplyDto.builder()
                .matching(matching.get())
                .siteUser(user.get())
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        var apply = applyRepository.save(Apply.fromDto(applyDto));

        var result = ApplyDto.fromEntity(apply);

        return result;
    }
}
