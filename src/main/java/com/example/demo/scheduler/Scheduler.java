package com.example.demo.scheduler;

import com.example.demo.apply.repository.ApplyRepository;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.notification.service.NotificationService;
import com.example.demo.type.NotificationType;
import com.example.demo.type.RecruitStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.ContinueResponseTiming;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class Scheduler {
    private final MatchingRepository matchingRepository;
    private static final DateTimeFormatter formForDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final NotificationService notificationService;
    private final ApplyRepository applyRepository;

    @Async
    @Scheduled(cron = "${scheduler.cron.matches.confirm}") // 매일 정시에 수행
    public void confirmResultsOfMatchesAtDueDate() {
        String now = LocalDateTime.now().format(formForDateTime);
        LocalDateTime recruitDueDateTime = LocalDateTime.parse(now, formForDateTime);
        log.info("scheduler is started at " + now);

        List<Matching> matchesForConfirm
                = matchingRepository.findAllByRecruitDueDateTime(recruitDueDateTime).get();

        if (!CollectionUtils.isEmpty(matchesForConfirm)) {
            changeStatusOfMatches(matchesForConfirm);
        }
    }

    private void changeStatusOfMatches(List<Matching> matchesForConfirm) {
        matchesForConfirm.stream()
                .forEach(matching
                        -> {
                    var applies = applyRepository.findAllByMatching_Id(matching.getId());

                    if (RecruitStatus.FULL.equals(matching.getRecruitStatus())) {
                        matching.changeRecruitStatus(RecruitStatus.CLOSED);
                        log.info("matching succeed -> " + matching.getId());

                        for (Apply apply : applies.get()) {
                            notificationService.createAndSendNotification(apply.getSiteUser(), matching,
                                    NotificationType.MATCHING_CLOSED);
                        }
                    } else {
                        matching.changeRecruitStatus(RecruitStatus.FAILED);
                        log.info("matching failed -> " + matching.getId());
                        for (Apply apply : applies.get()) {
                            notificationService.createAndSendNotification(apply.getSiteUser(), matching,
                                    NotificationType.MATCHING_FAILED);
                        }
                    }
                });
    }
}