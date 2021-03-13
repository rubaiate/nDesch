package com.nde.sch.TimeScheduleEverySecond;

import com.nde.sch.ScheduleManager;
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
    ScheduleManager scheduleManager;

    @Autowired
    TimedScheduleTestComponent testComponent;

    @Test
    void cancelScheduleSuccess() throws InterruptedException {
        Assertions.assertTrue(scheduleManager.cancelTimeSchedule("sch1"));
        Thread.sleep(100);
        long prevCount = testComponent.countDownLatch.getCount();
        Thread.sleep(1500);
        Assertions.assertEquals(prevCount, testComponent.countDownLatch.getCount());
        Assertions.assertFalse(scheduleManager.cancelTimeSchedule("sch1"));
    }

    @Test
    void cancelScheduleFail() {
        Assertions.assertFalse(scheduleManager.cancelTimeSchedule("sch2"));
    }

}
