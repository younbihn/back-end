package com.example.demo.matching.service;

import com.example.demo.matching.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl {
    private final MatchingRepository matchingRepository;

}