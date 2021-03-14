package com.nde.sch.TimeScheduleEverySecond;

import com.nde.sch.schedulerunner.TimeScheduleRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@EnableScheduling
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TimeScheduleCancelTest {
    @Autowired
    TimeScheduleRunner timeScheduleRunner;

    @Autowired
    TimedScheduleTestComponent testComponent;

    @Test
    void cancelScheduleSuccess() throws InterruptedException {
        Assertions.assertTrue(timeScheduleRunner.cancelTimeSchedule("sch1"));
        Thread.sleep(100);
        long prevCount = testComponent.countDownLatch.getCount();
        Thread.sleep(1500);
        Assertions.assertEquals(prevCount, testComponent.countDownLatch.getCount());
        Assertions.assertFalse(timeScheduleRunner.cancelTimeSchedule("sch1"));
    }

    @Test
    void cancelScheduleFail() {
        Assertions.assertFalse(timeScheduleRunner.cancelTimeSchedule("sch2"));
    }

}
