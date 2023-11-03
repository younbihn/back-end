package com.example.demo.matching.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.demo.aws.S3Uploader;
import com.example.demo.matching.dto.ApplyContents;
import com.example.demo.matching.service.MatchingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(MatchingController.class)
class MatchingControllerTest {
    @MockBean
    private MatchingService matchingService;

    @MockBean
    private S3Uploader s3Uploader;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void successConfirmMatching() throws Exception {
        // given
        given(matchingService.getApplyContents(anyLong(), anyLong()))
                .willReturn(ApplyContents.builder().build());

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/matches/1/apply"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}