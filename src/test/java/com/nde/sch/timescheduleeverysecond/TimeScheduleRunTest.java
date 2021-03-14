package com.nde.sch.timescheduleeverysecond;

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
@Import(TimedScheduleTestComponent.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TimeScheduleRunTest {
    @Autowired
    TimedScheduleTestComponent testComponent;

    @Test
    void contextLoads() throws InterruptedException {
        Assertions.assertFalse(testComponent.countDownLatch.await(2000, TimeUnit.MILLISECONDS));
        Assertions.assertTrue(testComponent.countDownLatch.await(3500, TimeUnit.MILLISECONDS));
    }

}
