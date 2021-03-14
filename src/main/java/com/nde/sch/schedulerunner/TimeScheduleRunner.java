package com.nde.sch.schedulerunner;

import com.nde.sch.ScheduleService;
import com.nde.sch.context.ScheduleContext;
import com.nde.sch.definitions.TimedScheduleDefinition;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TimeZone;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class TimeScheduleRunner {
    private final ScheduleContext scheduleContext;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final ScheduleService scheduleService;

    private final Map<String, ScheduledFuture<?>> jobScheduledFutures = new ConcurrentHashMap<>();

    public TimeScheduleRunner(ScheduleContext scheduleContext,
                              ThreadPoolTaskScheduler taskScheduler,
                              ScheduleService scheduleService) {
        this.scheduleContext = scheduleContext;
        this.taskScheduler = taskScheduler;
        this.scheduleService = scheduleService;
    }

    public boolean cancelTimeSchedule(String jobId) {
        var future = jobScheduledFutures.get(jobId);
        if (future != null) {
            boolean result = future.cancel(true);
            if (result) {
                jobScheduledFutures.remove(jobId);
            }
            return result;
        }
        return false;
    }

    @EventListener
    private void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        scheduleTimeSchedules();
    }

    private void scheduleTimeSchedules() {
        scheduleContext.getTimedSchedules().values().forEach(this::schedule);
    }

    private void schedule(TimedScheduleDefinition scheduleDefinition) {
        if (!scheduleDefinition.getScheduleEntity().getEnabled()) {
            return;
        }

        var future = taskScheduler.schedule(new TimerTask() {
            @Override
            public void run() {
                scheduleService.run(scheduleDefinition);
            }
        }, new CronTrigger(scheduleDefinition.getTimeExpression(), TimeZone.getTimeZone(scheduleDefinition.getTimeZone())));
        jobScheduledFutures.put(scheduleDefinition.getId(), future);
    }
}
