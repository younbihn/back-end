package com.example.demo.apply.service;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.response.ResponseDto;
import java.util.List;

public interface ApplyService {
    ResponseDto apply(long userId, long matchId);

    ResponseDto cancel(long applyId);

    ResponseDto accept(List<Long> appliedList, List<Long> confirmedList, long matchingId);
}
