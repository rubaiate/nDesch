package com.nde.sch.dependentschedule;

import com.nde.sch.service.ScheduleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@EnableScheduling
@Import(DependentScheduleTestComponent.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DependentScheduleRunTest {
    @Autowired
    DependentScheduleTestComponent testComponent;

    @Autowired
    ScheduleService scheduleService;

    @Test
    void runDependentTest() throws InterruptedException {
        scheduleService.run("sch1");
        Assertions.assertTrue(testComponent.countDownLatch.await(100, TimeUnit.MILLISECONDS));
    }

    @Test
    void notStartingDependentTest() throws InterruptedException {
        Assertions.assertFalse(testComponent.countDownLatch.await(100, TimeUnit.MILLISECONDS));
    }
}
