package com.example.demo.scheduler;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import com.example.demo.config.SchedulerConfig;
import com.example.demo.matching.repository.MatchingRepository;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


@SpringJUnitConfig(SchedulerConfig.class)
class SchedulerTest {

    @SpyBean
    private Scheduler scheduler;

    @MockBean
    MatchingRepository matchingRepository;
    @Test
    public void confirmMatchesTest() { // 테스트용 cron 설정으로 진행
        await().atMost(Duration.FIVE_SECONDS)
                .untilAsserted(()
                        -> verify(scheduler, atLeast(2)).confirmMatches());
    }
}