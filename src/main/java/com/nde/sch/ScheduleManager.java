package com.nde.sch;

import com.nde.sch.context.ScheduleContext;
import com.nde.sch.definitions.TimedScheduleDefinition;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TimeZone;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class ScheduleManager implements ApplicationListener<ApplicationReadyEvent> {
    private final ScheduleContext scheduleContext;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> jobScheduledFutures = new ConcurrentHashMap<>();

    public ScheduleManager(ScheduleContext scheduleContext, ThreadPoolTaskScheduler taskScheduler) {
        this.scheduleContext = scheduleContext;
        this.taskScheduler = taskScheduler;
    }

    public boolean cancelTimeSchedule(String jobId) {
        ScheduledFuture<?> future = jobScheduledFutures.get(jobId);
        if (future != null) {
            boolean result = future.cancel(true);
            if (result) {
                jobScheduledFutures.remove(jobId);
            }
            return result;
        }
        return false;
    }

    private void scheduleTimeSchedules() {
        scheduleContext.getTimedSchedules().values().forEach(this::schedule);
    }

    private void schedule(TimedScheduleDefinition scheduleDefinition) {
        ScheduledFuture<?> future = taskScheduler.schedule(new TimerTask() {
            @Override
            public void run() {
                scheduleDefinition.getJob().run();
            }
        }, new CronTrigger(scheduleDefinition.getTimeExpression(), TimeZone.getTimeZone(scheduleDefinition.getTimeZone())));
        jobScheduledFutures.put(scheduleDefinition.getId(), future);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        scheduleTimeSchedules();
    }
}
