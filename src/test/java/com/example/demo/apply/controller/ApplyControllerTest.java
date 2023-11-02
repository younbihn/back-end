package com.example.demo.apply.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.demo.apply.dto.AllLists;
import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.service.ApplyService;
import com.example.demo.response.ResponseUtil;
import com.example.demo.type.ApplyStatus;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ApplyController.class)
class ApplyControllerTest {
    @MockBean
    private ApplyService applyService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void successApply() throws Exception {
        // given
        given(applyService.apply(anyLong(), anyLong()))
                .willReturn(ResponseUtil.SUCCESS("매칭 신청에 성공하였습니다.", ApplyDto.builder()
                        .createTime(Timestamp.valueOf(LocalDateTime.now()))
                        .build()));
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/apply/matches/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void successCancelApply() throws Exception {
        // given
        given(applyService.cancel(anyLong()))
                .willReturn(ResponseUtil.SUCCESS("매칭 참가 신청을 취소하였습니다.", ApplyDto.builder()
                        .createTime(Timestamp.valueOf(LocalDateTime.now()))
                        .applyStatus(ApplyStatus.CANCELED)
                        .build()));
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/apply/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

//    @Test
//    void successAcceptApply() throws Exception {
//        // given
//        given(applyService.accept(anyList(), anyList(), anyLong()))
//                .willReturn(ResponseUtil.SUCCESS("수락 확정을 진행하였습니다.", null));
//        // when
//        List<Long> appliedList = new ArrayList<>();
//        List<Long> confirmedList = new ArrayList<>();
//        AllLists allLists = new AllLists();
//        allLists.setAppliedList(appliedList);
//        allLists.setConfirmedList(confirmedList);
//        // then
//        mockMvc.perform(MockMvcRequestBuilders.patch("/apply/matches/1"))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(appliedList)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(print());
//    }
}