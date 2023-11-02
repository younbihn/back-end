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
    public MatchingDetailDto create(Long userId, MatchingDetailDto matchingDetailDto) {
        SiteUser siteUser = validateUserGivenId(userId);
        matchingRepository.save(Matching.fromDto(matchingDetailDto, siteUser));
        return matchingDetailDto;
    }

    @Override
    public MatchingDetailDto update(Long userId, Long matchingId, MatchingDetailDto matchingDetailDto) {
        SiteUser siteUser = validateUserGivenId(userId);
        Matching matching = validateMatchingGivenId(matchingId);

        if(!isUserMadeThisMatching(matchingId, siteUser)){
            throw new NoPermissionToEditAndDeleteMatching();
        }

        matchingRepository.save(Matching.fromDto(matchingDetailDto, siteUser));
        return matchingDetailDto;
    }

    @Override
    public void delete(Long userId, Long matchingId) {
        SiteUser siteUser = validateUserGivenId(userId);
        Matching matching = validateMatchingGivenId(matchingId);

        if(!isUserMadeThisMatching(matchingId, siteUser)){
            throw new NoPermissionToEditAndDeleteMatching();
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

    private SiteUser validateUserGivenId(Long userId){
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