package com.example.demo.matching.service;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import com.example.demo.exception.impl.ApplyNotFoundException;
import com.example.demo.exception.impl.MatchingNotFoundException;
import com.example.demo.exception.impl.NoPermissionToEditAndDeleteMatching;
import com.example.demo.exception.impl.UserNotFoundException;
import com.example.demo.matching.dto.ApplyContents;
import com.example.demo.matching.dto.ApplyMember;
import com.example.demo.matching.dto.MatchingDetailDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.matching.repository.MatchingRepository;
import java.util.List;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.type.ApplyStatus;
import com.example.demo.common.FindEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingServiceImpl implements MatchingService {

    private final MatchingRepository matchingRepository;
    private final ApplyRepository applyRepository;
    private final FindEntity findEntity;
    private final SiteUserRepository siteUserRepository;

    @Override
    public Matching create(Long userId, MatchingDetailDto matchingDetailDto) {
        SiteUser siteUser = validateUserGivenId(userId);
        Matching matching = matchingRepository.save(Matching.fromDto(matchingDetailDto, siteUser));
        saveApplyForOrganizer(matching, siteUser);
        return matching;
    }

    private void saveApplyForOrganizer(Matching matching, SiteUser siteUser) {
        var applyDto = ApplyDto.builder()
                .matching(matching)
                .siteUser(siteUser)
                .build();
        Apply apply = applyRepository.save(Apply.fromDto(applyDto));
        apply.changeApplyStatus(ApplyStatus.ACCEPTED);
    }

    @Override
    public Matching update(Long userId, Long matchingId, MatchingDetailDto matchingDetailDto) {
        SiteUser siteUser = validateUserGivenId(userId);
        Matching matching = validateMatchingGivenId(matchingId);

        if(!isUserMadeThisMatching(matchingId, siteUser)){
            throw new NoPermissionToEditAndDeleteMatching();
        }

        //TODO : 매칭 글 수정 시 신청자들에게 알림

        matching.update(Matching.fromDto(matchingDetailDto, siteUser));
        return matchingRepository.save(matching);
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
        if (matching.getConfirmedNum() > 0) {
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

    @Override
    public ApplyContents getApplyContents(long userId, long matchingId) {
        var matching = findEntity.findMatching(matchingId);
        var recruitNum = matching.getRecruitNum();
        var confirmedNum = matching.getConfirmedNum();
        var applyNum = applyRepository.countByMatching_IdAndStatus(matchingId, ApplyStatus.PENDING).get();

        var appliedMembers = findAppliedMembers(matchingId);
        var confirmedMembers = findConfirmedMembers(matchingId);

        if (isOrganizer(userId, matching)) {
            var applyContentsForOrganizer = ApplyContents.builder()
                    .applyNum(applyNum)
                    .recruitNum(recruitNum)
                    .confirmedNum(confirmedNum)
                    .appliedMembers(appliedMembers)
                    .confirmedMembers(confirmedMembers)
                    .build();

            return applyContentsForOrganizer;
        }

        var applyContentsForUser = ApplyContents.builder()
                .recruitNum(recruitNum)
                .confirmedNum(confirmedNum)
                .confirmedMembers(confirmedMembers)
                .build();

            return applyContentsForUser;
        }

    private List<ApplyMember> findConfirmedMembers(long matchingId) {
        return applyRepository.findAllByMatching_IdAndStatus(matchingId, ApplyStatus.ACCEPTED)
                .get().stream().map((apply)
                        -> ApplyMember.builder()
                        .applyId(apply.getId())
                        .siteUserId(apply.getSiteUser().getId())
                        .nickname(apply.getSiteUser().getNickname())
                        .build()).collect(Collectors.toList());
    }

    private List<ApplyMember> findAppliedMembers(long matchingId) {
        return applyRepository.findAllByMatching_IdAndStatus(matchingId, ApplyStatus.PENDING)
                .orElseThrow(() -> new ApplyNotFoundException())
                .stream().map((apply)
                        -> ApplyMember.builder()
                        .applyId(apply.getId())
                        .siteUserId(apply.getSiteUser().getId())
                        .nickname(apply.getSiteUser().getNickname())
                        .build()).collect(Collectors.toList());
    }

    private static boolean isOrganizer(long userId, Matching matching) {
        return matching.getSiteUser().getId() == userId;
    }
}