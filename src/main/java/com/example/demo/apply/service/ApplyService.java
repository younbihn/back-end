package com.example.demo.apply.service;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.entity.Apply;
import java.util.List;

public interface ApplyService {
    Apply apply(long userId, long matchId);

    Apply cancel(long applyId);

    void accept(List<Long> appliedList, List<Long> confirmedList, long matchingId);
}
