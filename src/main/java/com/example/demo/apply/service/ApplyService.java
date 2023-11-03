package com.example.demo.apply.service;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.exception.impl.FailedApplyException;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.type.ApplyStatus;
import java.util.List;

public interface ApplyService {
    Apply apply(long userId, long matchId);

    Apply cancel(long applyId);

    Matching accept(List<Long> appliedList, List<Long> confirmedList, long matchingId);

}
