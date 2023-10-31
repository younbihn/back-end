package com.example.demo.apply.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.web.servlet.function.ServerResponse.status;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.service.ApplyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ApplyController.class)
class ApplyControllerTest {
    @MockBean
    private ApplyService applyService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
}