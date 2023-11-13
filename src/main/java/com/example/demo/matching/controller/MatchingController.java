package com.example.demo.matching.controller;

import com.example.demo.aws.S3Uploader;
import com.example.demo.exception.impl.S3UploadFailException;
import com.example.demo.matching.dto.ApplyContents;
import com.example.demo.matching.dto.KeywordDto;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.matching.dto.RoadAddressDto;
import com.example.demo.matching.service.AddressService;
import com.example.demo.matching.service.MatchingService;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/matches")
public class MatchingController {

    private final MatchingService matchingService;
    private final AddressService addressService;
    private final S3Uploader s3Uploader;

    @PostMapping
    public void createMatching (
            @RequestBody MatchingDetailDto matchingDetailDto,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        Long userId = 1L;
        matchingService.create(userId, matchingDetailDto);

        if(file != null){
            try{
                s3Uploader.uploadFile(file);
            } catch(IOException exception){
                throw new S3UploadFailException();
            }
        }
    }

    @GetMapping("/{matchingId}")
    public ResponseEntity<MatchingDetailDto> getDetailedMatching(
            @PathVariable Long matchingId){

        var result = matchingService.getDetail(matchingId);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{matchingId}")
    public void editMatching(
            @RequestBody MatchingDetailDto matchingDetailDto,
            @PathVariable Long matchingId,
            @RequestParam(value = "file", required = false) MultipartFile file){

        Long userId = 1L;
        matchingService.update(userId, matchingId, matchingDetailDto);

        // 구장 이미지 변경
        //TODO: 이미 존재하는 이미지인지 검증하는 로직이 이게 맞나..?
        if(file!=null && !file.toString().equals(matchingDetailDto.getLocationImg())){
            try{
                //TODO : S3에 있는 파일 삭제
                s3Uploader.uploadFile(file);
                matchingDetailDto.setLocationImg(file.toString());
            } catch(IOException exception){
                throw new S3UploadFailException();
            }
        }
    }

    @DeleteMapping("/{matchingId}")
    public void deleteMatching(
            @PathVariable Long matchingId){

        Long userId = 1L;

        matchingService.delete(userId, matchingId);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<MatchingPreviewDto>> getMatchingList(
            @PageableDefault(page = 0, size = 10) Pageable pageable){

        var result = matchingService.getList(pageable);

        return ResponseEntity.ok(result);
    }

    @SneakyThrows
    @GetMapping("/{matching_id}/apply")
    public ResponseEntity<ApplyContents> getApplyContents(@PathVariable(value = "matching_id") long matchingId) {

        Long userId = 1L;

        var result = matchingService.getApplyContents(userId, matchingId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/location")
    public ResponseEntity<List<RoadAddressDto>> getAddress(@RequestBody KeywordDto keywordDto) {

        var result = addressService.getAddress(keywordDto);

        return ResponseEntity.ok(result);
    }
}