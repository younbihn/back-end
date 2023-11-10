package com.example.demo.scheduler;

import com.example.demo.entity.Matching;
import com.example.demo.matching.repository.MatchingRepository;
import com.example.demo.type.RecruitStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class Scheduler {
    private final MatchingRepository matchingRepository;
    private static final DateTimeFormatter formForDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final List<Matching> nullCase = new ArrayList<>();

    @Async
    @Scheduled(cron = "${scheduler.cron.matches.confirm}") // 매일 정시에 수행
    public void confirmResultsOfMatchesAtDueDate() {
        String now = LocalDateTime.now().format(formForDateTime);
        LocalDateTime recruitDueDateTime = LocalDateTime.parse(now, formForDateTime);
        log.info("scheduler is started at " + now);

        List<Matching> matchesForConfirm
                = matchingRepository.findAllByRecruitDueDateTime(recruitDueDateTime).orElseGet(() -> nullCase);

        if (!matchesForConfirm.isEmpty()) {
            changeStatusOfMatches(matchesForConfirm);
        }
    }

    private void changeStatusOfMatches(List<Matching> matchesForConfirm) {
        matchesForConfirm.stream()
                .forEach(matching
                        -> {
                    if (RecruitStatus.FULL.equals(matching.getRecruitStatus())) {
                        matching.changeRecruitStatus(RecruitStatus.CLOSED);
                        log.info("matching succeed -> " + matching.getId());
                        //TODO: 매칭 확정 알림
                    } else {
                        matching.changeRecruitStatus(RecruitStatus.FAILED);
                        log.info("matching failed -> " + matching.getId());
                        //TODO: 매칭 실패 알림
                    }
                });
    }

}
