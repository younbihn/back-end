package com.example.demo.matching.controller;

import com.example.demo.aws.S3Uploader;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.service.MatchingService;
import com.example.demo.response.ResponseDto;
import com.example.demo.response.ResponseUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/matches")
public class MatchingController {
    private final MatchingService matchingService;
    private final S3Uploader s3Uploader;

    @PostMapping("")
    public ResponseEntity<MatchingDetailDto>  createMatching(
            @RequestBody MatchingDetailDto matchingDetailDto) {
        Long userId = 1L;
        var result = matchingService.create(userId, matchingDetailDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/matching/img")
    public String saveImg(@RequestParam("file") MultipartFile file) throws IOException {
        return s3Uploader.uploadFile(file);
    }

    @GetMapping("/{matching_id}/apply")
    public ResponseDto applyList(@PathVariable(value = "matching_id") long matchingId) {

        Long userId = 1L;

        var applyList = matchingService.getApplyList(userId, matchingId);

        return ResponseUtil.SUCCESS("매칭 신청 내역을 불러왔습니다.", applyList);
    }
}