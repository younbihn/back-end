package com.example.demo.service;

import com.example.demo.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl {
    private final MatchingRepository matchingRepository;

}
