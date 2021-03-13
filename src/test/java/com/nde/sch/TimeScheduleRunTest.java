package com.nde.sch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@EnableScheduling
class TimeScheduleRunTest {
    @Autowired
    TimedScheduleTestComponent testComponent;

    @Test
    void contextLoads() throws InterruptedException {
        Assertions.assertFalse(testComponent.countDownLatch.await(1000, TimeUnit.MILLISECONDS));
        Assertions.assertTrue(testComponent.countDownLatch.await(4000, TimeUnit.MILLISECONDS));
    }

}
