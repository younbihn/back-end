package com.example.demo.apply.service;

import com.example.demo.apply.dto.ApplyDto;

public interface ApplyService {
    void apply(long userId, long matchId);

    ApplyDto cancel(long userId, long applyId);
}
