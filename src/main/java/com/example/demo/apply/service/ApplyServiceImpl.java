package com.example.demo.apply.service;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import com.example.demo.exception.impl.AlreadyCanceledApplyException;
import com.example.demo.exception.impl.AlreadyClosedMatchingException;
import com.example.demo.exception.impl.AlreadyExistedApplyException;
import com.example.demo.exception.impl.ClosedMatchingException;
import com.example.demo.exception.impl.ApplyNotFoundException;
import com.example.demo.exception.impl.MatchingNotFoundException;
import com.example.demo.exception.impl.OverRecruitNumberException;
import com.example.demo.exception.impl.UserNotFoundException;
import com.example.demo.exception.impl.YourOwnPostingCancelException;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.type.ApplyStatus;
import com.example.demo.type.RecruitStatus;
import com.example.demo.util.FindEntityUtils;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplyServiceImpl implements ApplyService {

    private final ApplyRepository applyRepository;
    private final MatchingRepository matchingRepository;
    private final FindEntityUtils findEntityUtils;

    @Override
    public Apply apply(long userId, long matchingId) {
        var user = findEntityUtils.findUser(userId);
        var matching = findEntityUtils.findMatching(matchingId);

        validateRecruitStatus(matching); // 매칭 상태 검사

        if (isAlreadyExisted(userId, matchingId)) { // 신청 중복 검사
            var existApply = applyRepository.findBySiteUser_IdAndMatching_Id(userId, matchingId).get();
            validateApplyDuplication(existApply);
            existApply.setStatus(ApplyStatus.PENDING); // 취소 신청 내역 있을 경우 상태만 변경
            return existApply;
        }

        var applyDto = ApplyDto.builder()
                .matching(matching)
                .siteUser(user)
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        return applyRepository.save(Apply.fromDto(applyDto));
    }

    private boolean isAlreadyExisted(long userId, long matchingId) {
        return applyRepository.existsBySiteUser_IdAndMatching_Id(userId, matchingId);
    }

    private static void validateApplyDuplication(Apply existApply) {

        if (!existApply.getStatus().equals(ApplyStatus.CANCELED)) {
            throw new AlreadyExistedApplyException();
        }
    }

    private static void validateRecruitStatus(Matching matching) {
        if (matching.getRecruitStatus().equals(RecruitStatus.CLOSED)) {
            throw new ClosedMatchingException();
        }
    }

    @Override
    public Apply cancel(long applyId) {
        var apply = findEntityUtils.findApply(applyId);

        validateCancelDuplication(apply); // 취소 중복 검사

        var matching = apply.getMatching();

        validateYourOwnPosting(matching, apply);

        if (RecruitStatus.FULL.equals(matching.getRecruitStatus())) {
            //TODO: 패널티 부여
            apply.setStatus(ApplyStatus.CANCELED);
            return apply;
        }
        checkMatchingClosed(matching);

        apply.setStatus(ApplyStatus.CANCELED);
        return apply;
    }



    private static void checkMatchingClosed(Matching matching) {
        if (matching.getRecruitStatus().equals(RecruitStatus.CLOSED)) {
            throw new AlreadyClosedMatchingException();
        }
    }

    private static void validateYourOwnPosting(Matching matching, Apply apply) {
        if (matching.getSiteUser().getId() == apply.getSiteUser().getId()) {
            throw new YourOwnPostingCancelException();
        }
    }

    private static void validateCancelDuplication(Apply apply) {
        if (apply.getStatus().equals(ApplyStatus.CANCELED)) {
            throw new AlreadyCanceledApplyException();
        }
    }

    @Override
    public void accept(List<Long> appliedList, List<Long> confirmedList, long matchingId) {
        var matching = matchingRepository.findById(matchingId).get();
        var recruitNum = matching.getRecruitNum();
        var confirmedNum = confirmedList.size();

        validateOverRecruitNumber(recruitNum, confirmedNum);

        appliedList.stream()
                .forEach(applyId
                        -> applyRepository.findById(applyId).get().setStatus(ApplyStatus.PENDING));

        confirmedList.stream()
                .forEach(confirmedId
                        -> applyRepository.findById(confirmedId).get().setStatus(ApplyStatus.ACCEPTED));

        matching.setConfirmedNum(confirmedNum);
    }

    private static void validateOverRecruitNumber(int recruitNum, int confirmedNum) {
        if (confirmedNum > recruitNum) {
            throw new OverRecruitNumberException();
        }
    }
}
