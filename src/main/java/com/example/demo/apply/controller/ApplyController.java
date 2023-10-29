package com.example.demo.apply.controller;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping("/matches/{match_id}") // 매칭 참가 신청 api
    public ResponseEntity<ApplyDto> apply(@PathVariable(value = "match_id") long matchId) {

        long userId = 1;

        var result = applyService.apply(userId, matchId);

        return ResponseEntity.ok(result);
    }

}
