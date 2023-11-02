package com.example.demo.matching.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.aws.S3Uploader;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.service.MatchingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MatchingController.class)
class MatchingControllerTest {

    @MockBean
    private MatchingServiceImpl matchingService;

    @MockBean
    private S3Uploader s3Uploader;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("매칭글 등록")
    public void createMatchingTest() throws Exception {
        //given
        MatchingDetailDto matchingDetailDto = new MatchingDetailDto();
        given(matchingService.create(1L, matchingDetailDto))
                .willReturn(matchingDetailDto);
        given(s3Uploader.uploadFile(any()))
                .willReturn(anyString());
        String content = objectMapper.writeValueAsString(matchingDetailDto);

        //when
        //then
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
        //TODO: 공통 응답 확정되면 응답 형태 검증 로직 추가
    }

    @Test
    @DisplayName("매칭글 조회")
    public void getDetailedMatchingTest() throws Exception {
        //given
        MatchingDetailDto matchingDetailDto = new MatchingDetailDto();
        matchingDetailDto.setId(1L);

        mockMvc.perform(get("/api/matches/1"))
                .andDo(print());
    }
}