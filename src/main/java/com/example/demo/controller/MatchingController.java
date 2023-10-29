package com.example.demo.controller;

import com.example.demo.service.MatchingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MatchingController {
    private final MatchingServiceImpl matchingServiceImpl;



}
