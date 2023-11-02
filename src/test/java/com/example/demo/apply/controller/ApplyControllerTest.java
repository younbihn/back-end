package com.example.demo.apply.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.demo.apply.dto.AllLists;
import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.service.ApplyService;
import com.example.demo.entity.Matching;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.type.ApplyStatus;
import com.example.demo.type.RecruitStatus;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
                .willReturn(ApplyDto.builder()
                        .createTime(Timestamp.valueOf(LocalDateTime.now()))
                        .build());
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
                .willReturn(ApplyDto.builder()
                        .createTime(Timestamp.valueOf(LocalDateTime.now()))
                        .applyStatus(ApplyStatus.CANCELED)
                        .build());
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/apply/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void successAcceptApply() throws Exception {
        // given
        String request = "{\n"
                + "\"appliedList\": [1,2],\n"
                + "\"confirmedList\": [3,4]\n"
                + "}";

        given(applyService.accept(anyList(), anyList(), anyLong()))
                .willReturn(true);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.patch("/apply/matches/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}