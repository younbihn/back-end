package com.example.demo.apply.controller;

import com.example.demo.apply.dto.AllLists;
import com.example.demo.apply.service.ApplyService;
import com.example.demo.response.ResponseDto;
import com.example.demo.response.ResponseStatus;
import com.example.demo.response.ResponseUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping("/matches/{match_id}") // 매칭 참가 신청 api
    public ResponseDto apply(@PathVariable(value = "match_id") long matchingId) {

        long userId = 1; // 로그인 구현 전 임시로 부여

        applyService.apply(userId, matchingId);

        return ResponseUtil.SUCCESS("매칭 참가 신청에 성공하였습니다.", null);
    }

    @DeleteMapping("/{apply_id}") // 매칭 참가 신청 취소 api => 경기 확정
    public ResponseDto cancelApply(@PathVariable(value = "apply_id") long applyId) {

        applyService.cancel(applyId);

        return ResponseUtil.SUCCESS("매칭 참가 신청을 취소하였습니다.", null);
    }

    @PatchMapping("/matches/{matching_id}") // 참가 신청 수락 api
    public ResponseDto acceptApply(@RequestBody AllLists allLists,
                                   @PathVariable(value = "matching_id") long matchingId) {

        List<Long> appliedList = allLists.getAppliedList();
        List<Long> confirmedList = allLists.getConfirmedList();

        applyService.accept(appliedList, confirmedList, matchingId);

        return ResponseUtil.SUCCESS("수락 확정을 진행하였습니다.", null);
    }
}
