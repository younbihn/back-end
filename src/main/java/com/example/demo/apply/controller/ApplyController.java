package com.example.demo.apply.controller;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.service.ApplyService;
import com.example.demo.response.ResponseDto;
import com.example.demo.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping("/matches/{match_id}") // 매칭 참가 신청 api
    public ResponseDto apply(@PathVariable(value = "match_id") long matchId) {

        long userId = 1; // 로그인 구현 전 임시로 부여
        applyService.apply(userId, matchId);

        return ResponseUtil.SUCCESS("매칭 참가 신청에 성공하였습니다.", null);
    }

    @DeleteMapping("/{apply_id}") // 매칭 참가 신청 취소 api
    public ResponseEntity<ApplyDto> cancelApply(@PathVariable(value = "apply_id") long applyId) {

        long userId = 1;

        var result = applyService.cancel(userId, applyId);

        return ResponseEntity.ok(result);
    }
}
