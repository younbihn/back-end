package com.example.demo.matching.service;

import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import com.example.demo.exception.impl.MatchingNotFoundException;
import com.example.demo.exception.impl.NoPermissionToEditAndDeleteMatching;
import com.example.demo.exception.impl.UserNotFoundException;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

    private final MatchingRepository matchingRepository;
    private final SiteUserRepository siteUserRepository;

    @Override
    public void create(Long userId, MatchingDetailDto matchingDetailDto) {
        SiteUser siteUser = validateUserGivenId(userId);
        Matching matching = matchingRepository.save(Matching.fromDto(matchingDetailDto, siteUser));
    }

    @Override
    public void update(Long userId, Long matchingId, MatchingDetailDto matchingDetailDto) {
        SiteUser siteUser = validateUserGivenId(userId);
        Matching matching = validateMatchingGivenId(matchingId);

        if(!isUserMadeThisMatching(matchingId, siteUser)){
            throw new NoPermissionToEditAndDeleteMatching();
        }

        //TODO : 매칭 글 수정 시 신청자들에게 알림

        matching.update(Matching.fromDto(matchingDetailDto, siteUser));
        Matching savedMatching = matchingRepository.save(matching);
    }

    @Override
    public void delete(Long userId, Long matchingId) {
        SiteUser siteUser = validateUserGivenId(userId);
        Matching matching = validateMatchingGivenId(matchingId);

        if(!isUserMadeThisMatching(matchingId, siteUser)){
            throw new NoPermissionToEditAndDeleteMatching();
        }

        //TODO : 신청자 존재하는데 매칭 글 삭제 시 신청자들에게 알림
        //TODO : 신청자 존재하는데 매칭 글 삭제 시 패널티 부여
        if (matching.getApplyNum()>0) {
            //TODO : 매칭에 신청한 유저들의 매칭 해제
        }

        matchingRepository.delete(matching);
    }

    @Override
    public Page<MatchingPreviewDto> getList(Pageable pageable) {
        return matchingRepository.findAll(pageable)
                .map(MatchingPreviewDto::fromEntity);
    }

    @Override
    public MatchingDetailDto getDetail(Long matchingId) {
        Matching matching = validateMatchingGivenId(matchingId);
        return MatchingDetailDto.fromEntity(matching);
    }

    public SiteUser validateUserGivenId(Long userId){
        SiteUser siteUser = siteUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        return siteUser;
    }

    private Matching validateMatchingGivenId(Long matchingId){
        Matching matching = matchingRepository.findById(matchingId)
                .orElseThrow(() -> new MatchingNotFoundException());
        return matching;
    }

    private boolean isUserMadeThisMatching(Long matchingId, SiteUser siteUser){
        return matchingRepository.existsByIdAndSiteUser(matchingId, siteUser);
    }
}