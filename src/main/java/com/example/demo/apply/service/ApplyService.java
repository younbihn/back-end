package com.example.demo.apply.service;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.response.ResponseDto;
import java.util.List;

public interface ApplyService {
    ApplyDto apply(long userId, long matchId);

    ApplyDto cancel(long applyId);

    boolean accept(List<Long> appliedList, List<Long> confirmedList, long matchingId);
}
