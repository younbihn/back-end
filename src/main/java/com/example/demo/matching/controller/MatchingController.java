package com.example.demo.matching.controller;

import com.example.demo.matching.service.MatchingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MatchingController {
    private final MatchingServiceImpl matchingServiceImpl;
}