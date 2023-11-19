package com.example.demo.aws.controller;

import com.example.demo.aws.S3Uploader;
import com.example.demo.common.ResponseDto;
import com.example.demo.common.ResponseUtil;
import com.example.demo.entity.Auth;
import com.example.demo.exception.impl.S3UploadFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/aws")
@RequiredArgsConstructor
public class AwsController {
    private final S3Uploader s3Uploader;

    @PostMapping("/upload-image")
    public ResponseDto<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
        try {
            String profileImageUrl = s3Uploader.uploadFile(imageFile);
            return ResponseUtil.SUCCESS(profileImageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            throw new S3UploadFailException();
        }
    }
}
