package com.example.demo.apply.service;

import com.example.demo.apply.dto.ApplyDto;

public interface ApplyService {
    ApplyDto apply(long userId, long matchId);
}
