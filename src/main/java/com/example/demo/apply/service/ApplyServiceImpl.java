package com.example.demo.apply.service;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.exception.impl.AlreadyCanceledApplyException;
import com.example.demo.exception.impl.ClosedMatchingException;
import com.example.demo.exception.impl.NonExistedApplyException;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.response.ResponseDto;
import com.example.demo.response.ResponseUtil;
import com.example.demo.type.ApplyStatus;
import com.example.demo.type.RecruitStatus;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    @Override
    public ResponseDto apply(long userId, long matchingId) {
        var user = siteUserRepository.findById(userId);
        var matching = matchingRepository.findById(matchingId);

        checkRecruitStatus(matching); // 매칭 상태 검사
        boolean exists = applyRepository.existsBySiteUser_IdAndMatching_Id(userId, matchingId);

        if (exists) { // 신청 중복 검사
            var existApply = applyRepository.findBySiteUser_IdAndMatching_Id(userId, matchingId).get();
            if (!existApply.getStatus().equals(ApplyStatus.CANCELED)) {
                return ResponseUtil
                        .FAILURE("이미 신청한 매칭 내역이 존재합니다.", ApplyDto.fromEntity(existApply));
            }
            existApply.setStatus(ApplyStatus.PENDING); // 취소 신청 내역 있을 경우 상태만 변경
            return ResponseUtil.SUCCESS("매칭 신청에 성공하였습니다.", ApplyDto.fromEntity(existApply));
        }

        var applyDto = ApplyDto.builder()
                .matching(matching.get())
                .siteUser(user.get())
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        var apply = applyRepository.save(Apply.fromDto(applyDto));
        return ResponseUtil.SUCCESS("매칭 신청에 성공하였습니다.", ApplyDto.fromEntity(apply));
    }

    private static void checkRecruitStatus(Optional<Matching> matching) {
        if (matching.get().getRecruitStatus().equals(RecruitStatus.CLOSED)) {
            throw new ClosedMatchingException();
        }
    }

    @Override
    public ResponseDto cancel(long applyId) {
        var apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NonExistedApplyException());

        if (apply.getStatus().equals(ApplyStatus.CANCELED)) {
            throw new AlreadyCanceledApplyException();
        }
        var matchingId = apply.getMatching().getId();
        var matching = matchingRepository.findById(matchingId).get();

        if (matching.getRecruitStatus().equals(RecruitStatus.CLOSED)) {
            if (matching.getDate().after(Date.valueOf(LocalDate.now()))) {
                //TODO: 패널티 부여
                apply.setStatus(ApplyStatus.CANCELED);
            }
            return ResponseUtil.FAILURE("매칭 당일에는 매칭 취소가 불가능합니다.", ApplyDto.fromEntity(apply));
        }
        apply.setStatus(ApplyStatus.CANCELED);
        return ResponseUtil.SUCCESS("매칭 참가 신청을 취소하였습니다.", ApplyDto.fromEntity(apply));
    }

    @Override
    public ResponseDto accept(List<Long> appliedList, List<Long> confirmedList, long matchingId) {
        // 신청 내역으로 옮겨진 id 받아와서 전부 상태 변경 및 매칭 확정 수/매칭 상태 변경
        var matching = matchingRepository.findById(matchingId).get();
        var recruitNum = matching.getRecruitNum() - 1;
        var confirmedNum = confirmedList.size();
        if (recruitNum < confirmedNum) {
            return ResponseUtil.FAILURE("모집 인원보다 많은 인원을 수락할 수 없습니다.", null);
        }
        for (long applyId : appliedList) {
            var apply = applyRepository.findById(applyId).get();
            apply.setStatus(ApplyStatus.PENDING);
        }
        for (long confirmId : confirmedList) {
            var apply = applyRepository.findById(confirmId).get();
            apply.setStatus(ApplyStatus.ACCEPTED);
        }
        matching.setConfirmedNum(confirmedNum + 1);
        return ResponseUtil.SUCCESS("수락 확정을 진행하였습니다.", null);
    }
}
