package com.example.demo.siteuser.controller;

import com.example.demo.aws.S3Uploader;
import com.example.demo.entity.Auth;
import com.example.demo.siteuser.security.TokenProvider;
import com.example.demo.siteuser.service.MemberService;
import com.example.demo.type.Authority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final S3Uploader s3Uploader;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {
        // 회원가입을 위한 API
        var result = this.memberService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        // 로그인용 API
        var member = this.memberService.authenticate(request);
        //List<String> role = new ArrayList<>();
        //role.add(member.getRoles().toString());
        var token = this.tokenProvider.generateToken(member.getEmail(), member.getRoles());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/upload-profile-image")
    public ResponseEntity<?> uploadOrUpdateProfileImage(@RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // 새 이미지 업로드 및 URL 반환
            String newImageUrl = s3Uploader.uploadFile(imageFile);

            return new ResponseEntity<>(newImageUrl, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
