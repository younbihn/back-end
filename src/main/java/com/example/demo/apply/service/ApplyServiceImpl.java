package com.example.demo.apply.service;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.exception.impl.AlreadyCanceledApplyException;
import com.example.demo.exception.impl.AlreadyExistedApplyException;
import com.example.demo.exception.impl.CancellationOnGameDayException;
import com.example.demo.exception.impl.ClosedMatchingException;
import com.example.demo.exception.impl.NonExistedApplyException;
import com.example.demo.exception.impl.OverRecruitNumberException;
import com.example.demo.exception.impl.YourOwnPostingCancelException;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
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
    public ApplyDto apply(long userId, long matchingId) {
        var user = siteUserRepository.findById(userId);
        var matching = matchingRepository.findById(matchingId);

        checkRecruitStatus(matching); // 매칭 상태 검사

        if (isAlreadyExisted(userId, matchingId)) { // 신청 중복 검사
            var existApply = applyRepository.findBySiteUser_IdAndMatching_Id(userId, matchingId).get();
            checkApplyDuplication(existApply);
            existApply.setStatus(ApplyStatus.PENDING); // 취소 신청 내역 있을 경우 상태만 변경
            return ApplyDto.fromEntity(existApply);
        }

        var applyDto = ApplyDto.builder()
                .matching(matching.get())
                .siteUser(user.get())
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        var apply = applyRepository.save(Apply.fromDto(applyDto));
        return ApplyDto.fromEntity(apply);
    }

    private boolean isAlreadyExisted(long userId, long matchingId) {
        return applyRepository.existsBySiteUser_IdAndMatching_Id(userId, matchingId);
    }

    private static void checkApplyDuplication(Apply existApply) {

        if (!existApply.getStatus().equals(ApplyStatus.CANCELED)) {
            throw new AlreadyExistedApplyException();
        }
    }

    private static void checkRecruitStatus(Optional<Matching> matching) {
        if (matching.get().getRecruitStatus().equals(RecruitStatus.CLOSED)) {
            throw new ClosedMatchingException();
        }
    }

    @Override
    public ApplyDto cancel(long applyId) {
        var apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NonExistedApplyException());

        checkCancelDuplication(apply); // 취소 중복 검사

        var matchingId = apply.getMatching().getId();
        var matching = matchingRepository.findById(matchingId).get();

        checkYourOwnPosting(matching, apply);

        if (matching.getRecruitStatus().equals(RecruitStatus.CLOSED)) {
            if (matching.getDate().after(Date.valueOf(LocalDate.now()))) {
                //TODO: 패널티 부여
                apply.setStatus(ApplyStatus.CANCELED);
            }
            throw new CancellationOnGameDayException();
        }
        apply.setStatus(ApplyStatus.CANCELED);
        return ApplyDto.fromEntity(apply);
    }

    private static void checkYourOwnPosting(Matching matching, Apply apply) {
        if (matching.getSiteUser().getId() == apply.getSiteUser().getId()) {
            throw new YourOwnPostingCancelException();
        }
    }

    private static void checkCancelDuplication(Apply apply) {
        if (apply.getStatus().equals(ApplyStatus.CANCELED)) {
            throw new AlreadyCanceledApplyException();
        }
    }

    @Override
    public void accept(List<Long> appliedList, List<Long> confirmedList, long matchingId) {
        // 신청 내역으로 옮겨진 id 받아와서 전부 상태 변경 및 매칭 확정 수/매칭 상태 변경
        var matching = matchingRepository.findById(matchingId).get();
        var recruitNum = matching.getRecruitNum();
        var confirmedNum = confirmedList.size();

        checkOverRecruitNumber(recruitNum, confirmedNum);

        for (long applyId : appliedList) {
            var apply = applyRepository.findById(applyId).get();
            apply.setStatus(ApplyStatus.PENDING);
        }
        for (long confirmId : confirmedList) {
            var apply = applyRepository.findById(confirmId).get();
            apply.setStatus(ApplyStatus.ACCEPTED);
        }

        matching.setConfirmedNum(confirmedNum);
        revieewRecruitStatus(confirmedNum, recruitNum, matching);
    }

    private static void revieewRecruitStatus(int confirmedNum, Integer recruitNum, Matching matching) {
        if (confirmedNum == recruitNum) {
            matching.setRecruitStatus(RecruitStatus.CLOSED);
        }
    }

    private static void checkOverRecruitNumber(int recruitNum, int confirmedNum) {
        if (confirmedNum > recruitNum) {
            throw new OverRecruitNumberException();
        }
    }
}
