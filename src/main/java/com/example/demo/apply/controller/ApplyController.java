package com.example.demo.apply.controller;

import com.example.demo.apply.dto.AppliedListAndConfiredList;
import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.service.ApplyService;
import com.example.demo.entity.Apply;
import com.example.demo.exception.impl.FailedApplyCancelException;
import com.example.demo.exception.impl.FailedApplyException;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.type.ApplyStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
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
    public void apply(@PathVariable(value = "match_id") long matchingId) {

        long userId = 1; // 로그인 구현 전 임시로 부여
        applyService.apply(userId, matchingId);
    }



    @DeleteMapping("/{apply_id}") // 매칭 참가 신청 취소 api => 경기 확정
    public void cancelApply(@PathVariable(value = "apply_id") long applyId) {

        ApplyDto.fromEntity(applyService.cancel(applyId));
    }



    @PatchMapping("/matches/{matching_id}") // 참가 신청 수락 api
    public void acceptApply(@RequestBody AppliedListAndConfiredList appliedListAndConfiredList,
                                   @PathVariable(value = "matching_id") long matchingId) {

        List<Long> appliedList = appliedListAndConfiredList.getAppliedList();
        List<Long> confirmedList = appliedListAndConfiredList.getConfirmedList();

        applyService.accept(appliedList, confirmedList, matchingId);
    }
}
