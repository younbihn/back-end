package com.example.demo.matching.controller;

import com.example.demo.matching.dto.ApplyContents;
import com.example.demo.matching.service.MatchingService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    void successConfirmMatching() throws Exception {
        // given
        given(matchingService.getApplyContents(anyLong(), anyLong()))
                .willReturn(ApplyContents.builder().build());

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/matches/1/apply"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

    @Test
    @DisplayName("매칭글 등록 - 구장 이미지 없는 경우")
    public void createMatchingTest() throws Exception {
        //given
        MatchingDetailDto matchingDetailDto = makeMatchingDetailDto();
        given(matchingService.create(anyLong(), any(MatchingDetailDto.class)))
                .willReturn(matchingDetailDto);
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
        MatchingDetailDto matchingDetailDto = makeMatchingDetailDto();
        given(matchingService.getDetail(1L))
                .willReturn(matchingDetailDto);
        //when
        //then
        mockMvc.perform(get("/api/matches/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(matchingDetailDto.getTitle()))
                .andExpect(jsonPath("$.content").value(matchingDetailDto.getContent()))
                .andExpect(jsonPath("$.location").value(matchingDetailDto.getLocation()))
                .andExpect(jsonPath("$.locationImg").value(matchingDetailDto.getLocationImg()))
                .andExpect(jsonPath("$.date").value(matchingDetailDto.getDate().toString()))
                .andExpect(jsonPath("$.startTime").value(matchingDetailDto.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                .andExpect(jsonPath("$.endTime").value(matchingDetailDto.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                .andExpect(jsonPath("$.recruitNum").value(matchingDetailDto.getRecruitNum()))
                .andExpect(jsonPath("$.cost").value(matchingDetailDto.getCost()))
                .andExpect(jsonPath("$.isReserved").value(matchingDetailDto.getIsReserved()))
                .andExpect(jsonPath("$.ntrp").value(matchingDetailDto.getNtrp()))
                .andExpect(jsonPath("$.ageGroup").value(matchingDetailDto.getAgeGroup()))
                .andExpect(jsonPath("$.recruitStatus").value(matchingDetailDto.getRecruitStatus().name()))
                .andExpect(jsonPath("$.matchingType").value(matchingDetailDto.getMatchingType().name()))
                .andExpect(jsonPath("$.applyNum").value(matchingDetailDto.getApplyNum()));
    }

    @Test
    @DisplayName("매칭글 수정 - 구장 이미지 바꾸지 않은 경우")
    public void editMatchingTest() throws Exception {
        //given
        MatchingDetailDto matchingDetailDto = makeMatchingDetailDto();
        given(matchingService.update(anyLong(), anyLong(), any(MatchingDetailDto.class)))
                .willReturn(matchingDetailDto);
        String content = objectMapper.writeValueAsString(matchingDetailDto);

        //when
        //then
        mockMvc.perform(patch("/api/matches/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
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
        MatchingPreviewDto matchingPreviewDto = makeMatchingPreviewDto();
        arrayList.add(matchingPreviewDto);
        Page<MatchingPreviewDto> pages = new PageImpl<>(arrayList, pageable, 1);

        given(matchingService.getList(pageable))
                .willReturn(pages);

        mockMvc.perform(get("/api/matches/list")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].ntrp").value(matchingPreviewDto.getNtrp()))
                .andExpect(jsonPath("$.content[0].title").value(matchingPreviewDto.getTitle()))
                .andExpect(jsonPath("$.content[0].matchingStartDateTime").value(matchingPreviewDto.getMatchingStartDateTime()))
                .andExpect(jsonPath("$.content[0].reserved").value(matchingPreviewDto.isReserved()))
                .andExpect(jsonPath("$.content[0].matchingType").value(matchingPreviewDto.getMatchingType().name()));
    }

    private MatchingDetailDto makeMatchingDetailDto(){
        return MatchingDetailDto.builder()
                .id(1L)
                .creatorUserId(2L)
                .title("제목")
                .content("본문")
                .location("장소")
                .locationImg("구장 이미지 주소")
                .date(LocalDate.now())
                .startTime(LocalTime.of(10,0))
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
    }

    private MatchingPreviewDto makeMatchingPreviewDto(){
        return MatchingPreviewDto.builder()
                .isReserved(true)
                .matchingType(MatchingType.SINGLE)
                .ntrp("ntrp")
                .title("제목")
                .matchingStartDateTime("시작 날짜")
                .build();
    }
}