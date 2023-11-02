package com.example.demo.matching.service;

import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import com.example.demo.exception.impl.NoPermissionToEditAndDeleteMatching;
import com.example.demo.exception.impl.UserNotFoundException;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.repository.SiteUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public MatchingDetailDto update(Long userId, MatchingDetailDto matchingDetailDto) {
        SiteUser siteUser = validateUserGivenId(userId);
        if(!isUserMadeThisMatching(matchingDetailDto.getId(), siteUser)){
            throw new NoPermissionToEditAndDeleteMatching();
        }
        matchingRepository.save(Matching.fromDto(matchingDetailDto, siteUser));
        return matchingDetailDto;
    }

    @Override
    public void delete(Long userId, Long matchingId) {

    }

    @Override
    public List<MatchingPreviewDto> getList() {
        return null;
    }

    @Override
    public MatchingDetailDto getDetail(Long matchingId) {
        return null;
    }

    private SiteUser validateUserGivenId(Long userId){
        SiteUser siteUser = siteUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        return siteUser;
    }

    private boolean isUserMadeThisMatching(Long matchingId, SiteUser siteUser){
        return matchingRepository.existsByIdAndSiteUser(matchingId, siteUser);
    }
}