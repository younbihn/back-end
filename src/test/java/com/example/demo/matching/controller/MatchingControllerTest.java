package com.example.demo.matching.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.aws.S3Uploader;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.matching.service.MatchingServiceImpl;
import com.example.demo.type.MatchingType;
import com.example.demo.type.RecruitStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MatchingController.class)
class MatchingControllerTest { //TODO: 공통 응답 확정되면 응답 형태 검증 로직 추가

    @MockBean
    private MatchingServiceImpl matchingService;

    @MockBean
    private S3Uploader s3Uploader;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    MatchingDetailDto matchingDetailDto = MatchingDetailDto.builder()
            .id(1L)
            .creatorUserId(2L)
            .title("제목")
            .content("본문")
            .location("장소")
            .locationImg("구장 이미지 주소")
            .date(LocalDate.now())
            .startTime(LocalTime.of(10, 0))
            .endTime(LocalTime.of(12, 0))
            .recruitNum(5)
            .cost(5000)
            .isReserved(false)
            .ntrp("Developer")
            .ageGroup("TWENTIES")
            .recruitStatus(RecruitStatus.OPEN)
            .matchingType(MatchingType.SINGLE)
            .applyNum(2)
            .createTime(LocalDateTime.now())
            .build();

    MatchingPreviewDto matchingPreviewDto = MatchingPreviewDto.builder()
            .isReserved(true)
            .matchingType(MatchingType.SINGLE)  // MatchingType에 맞는 값을 설정해주세요.
            .ntrp("ntrp")
            .title("제목")
            .matchingStartDateTime("시작 날짜")
            .build();

    @Test
    @DisplayName("매칭글 등록")
    public void createMatchingTest() throws Exception {
        //given
        given(matchingService.create(1L, any(MatchingDetailDto.class)))
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
    }

    @Test
    @DisplayName("매칭글 조회")
    public void getDetailedMatchingTest() throws Exception {
        //given
        given(matchingService.getDetail(1L))
                .willReturn(matchingDetailDto);
        //when
        //then
        mockMvc.perform(get("/api/matches/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.location").exists())
                .andExpect(jsonPath("$.locationImg").exists())
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.startTime").exists())
                .andExpect(jsonPath("$.endTime").exists())
                .andExpect(jsonPath("$.recruitNum").exists())
                .andExpect(jsonPath("$.cost").exists())
                .andExpect(jsonPath("$.isReserved").exists())
                .andExpect(jsonPath("$.ntrp").exists())
                .andExpect(jsonPath("$.ageGroup").exists())
                .andExpect(jsonPath("$.recruitStatus").exists())
                .andExpect(jsonPath("$.ageGroup").exists())
                .andExpect(jsonPath("$.matchingType").exists())
                .andExpect(jsonPath("$.applyNum").exists());
    }

    @Test
    @DisplayName("매칭글 수정 - 구장 이미지 바꾸지 않은 경우")
    public void editMatchingTest() throws Exception {
        //given
        given(matchingService.update(1L, 1L, any(MatchingDetailDto.class)))
                .willReturn(matchingDetailDto);

        //when
        //then
        mockMvc.perform(patch("/api/matches/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.location").exists())
                .andExpect(jsonPath("$.locationImg").exists())
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.startTime").exists())
                .andExpect(jsonPath("$.endTime").exists())
                .andExpect(jsonPath("$.recruitNum").exists())
                .andExpect(jsonPath("$.cost").exists())
                .andExpect(jsonPath("$.isReserved").exists())
                .andExpect(jsonPath("$.ntrp").exists())
                .andExpect(jsonPath("$.ageGroup").exists())
                .andExpect(jsonPath("$.recruitStatus").exists())
                .andExpect(jsonPath("$.ageGroup").exists())
                .andExpect(jsonPath("$.matchingType").exists())
                .andExpect(jsonPath("$.applyNum").exists());
    }

    @Test
    @DisplayName("매칭글 삭제")
    public void deleteMatchingTest() throws Exception {
        //given
        given(matchingService.delete(1L, 1L))
                .willReturn(1L);

        mockMvc.perform(delete("/api/matches/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("매칭글 목록 조회")
    public void getMatchingList() throws Exception {
        //given
        int page = 0;
        int size = 1;
        Pageable pageable = PageRequest.of(page, size);
        ArrayList<MatchingPreviewDto> arrayList = new ArrayList<>();
        arrayList.add(matchingPreviewDto);
        Page<MatchingPreviewDto> pages = new PageImpl<>(arrayList, pageable, 1);

        given(matchingService.getList(pageable))
                .willReturn(pages);

        mockMvc.perform(get("/api/matches/list")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].isReserved").value(matchingPreviewDto.getTitle()))
                .andExpect(jsonPath("$.content[0].matchingType").value(matchingPreviewDto.getMatchingType()))
                .andExpect(jsonPath("$.content[0].ntrp").value(matchingPreviewDto.getNtrp()))
                .andExpect(jsonPath("$.content[0].title").value(matchingPreviewDto.getTitle()))
                .andExpect(jsonPath("$.content[0].matchingStartDateTime").value(matchingPreviewDto.getMatchingStartDateTime()));
    }
}