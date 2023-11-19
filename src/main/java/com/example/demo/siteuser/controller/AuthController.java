package com.example.demo.siteuser.controller;

import com.example.demo.aws.S3Uploader;
import com.example.demo.common.ResponseDto;
import com.example.demo.common.ResponseUtil;
import com.example.demo.entity.Auth;
import com.example.demo.siteuser.dto.EmailRequestDto;
import com.example.demo.siteuser.dto.NicknameRequestDto;
import com.example.demo.siteuser.security.TokenProvider;
import com.example.demo.siteuser.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public ResponseEntity<ResponseDto<String>> signup(@RequestBody Auth.SignUp request) {
        try {
            memberService.register(request);
            return ResponseEntity.ok(ResponseUtil.SUCCESS("회원 가입 성공"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResponseUtil.SUCCESS("회원 가입 실패"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload-image")
    public ResponseEntity<ResponseDto<String>> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
        try {
            String profileImageUrl = s3Uploader.uploadFile(imageFile);
            return ResponseEntity.ok(ResponseUtil.SUCCESS(profileImageUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResponseUtil.SUCCESS("이미지 업로드 실패"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @PostMapping(path = "/check-email")
    public ResponseEntity<ResponseDto<String>> checkEmailExistence(@RequestBody EmailRequestDto emailRequestDto) {
        boolean exists = memberService.isEmailExist(emailRequestDto.getEmail());
        String message = exists ? "사용 불가능한 이메일 입니다." : "사용 가능한 이메일 입니다.";
        return ResponseEntity.ok(ResponseUtil.SUCCESS(message));
    }

    @PostMapping(path = "/check-nickname")
    public ResponseEntity<ResponseDto<String>> checkNicknameExistence(@RequestBody NicknameRequestDto nicknameRequestDto) {
        boolean exists = memberService.isNicknameExist(nicknameRequestDto.getNickname());
        String message = exists ? "사용 불가능한 닉네임 입니다." : "사용 가능한 닉네임 입니다.";
        return ResponseEntity.ok(ResponseUtil.SUCCESS(message));
    }
}
