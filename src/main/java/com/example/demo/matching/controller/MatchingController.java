package com.example.demo.matching.controller;

import com.example.demo.aws.S3Uploader;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.service.MatchingServiceImpl;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class MatchingController {
    private final MatchingServiceImpl matchingServiceImpl;
    private final S3Uploader s3Uploader;

    @PostMapping("")
    public ResponseEntity<MatchingDetailDto>  createMatching(
            @RequestBody MatchingDetailDto matchingDetailDto) {
        Long userId = 1L;
        var result = matchingServiceImpl.create(userId, matchingDetailDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/matching/img")
    public String saveImg(@RequestParam("file") MultipartFile file) throws IOException {
        return s3Uploader.uploadFile(file);
    }
}