package com.example.demo.matching.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.aws.S3Uploader;
import com.example.demo.entity.SiteUser;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingMyHostedDto;
import com.example.demo.matching.service.MatchingService;
import com.example.demo.matching.service.MatchingServiceImpl;
import java.io.IOException;
import java.util.List;

import com.example.demo.siteuser.dto.SiteUserMyInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class MatchingController {
    private final MatchingServiceImpl matchingServiceImpl;
    private final S3Uploader s3Uploader;
    private final MatchingService matchingService;

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

    @GetMapping("/hosted/{siteUser}")
    public ResponseEntity<MatchingMyHostedDto> getBySiteUser(@PathVariable(value = "siteUser") SiteUser siteUser) {
        MatchingMyHostedDto matchingMyHostedDto = matchingService.getBySiteUser(siteUser);

        if (matchingMyHostedDto != null) {
            return new ResponseEntity<>(matchingMyHostedDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}