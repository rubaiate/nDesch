package com.nde.sch.schedulerunner;

import com.nde.sch.ScheduleEvent;
import com.nde.sch.context.ScheduleContext;
import com.nde.sch.definitions.TimedScheduleDefinition;
import com.nde.sch.enums.ScheduleStatus;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
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
public class TimeScheduleRunner implements ApplicationListener<ApplicationReadyEvent> {
    private final ScheduleContext scheduleContext;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final Map<String, ScheduledFuture<?>> jobScheduledFutures = new ConcurrentHashMap<>();

    public TimeScheduleRunner(ScheduleContext scheduleContext, ThreadPoolTaskScheduler taskScheduler, ApplicationEventPublisher applicationEventPublisher) {
        this.scheduleContext = scheduleContext;
        this.taskScheduler = taskScheduler;
        this.applicationEventPublisher = applicationEventPublisher;
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

    private void scheduleTimeSchedules() {
        scheduleContext.getTimedSchedules().values().forEach(this::schedule);
    }

    private void schedule(TimedScheduleDefinition scheduleDefinition) {
        var future = taskScheduler.schedule(new TimerTask() {
            @Override
            public void run() {
                applicationEventPublisher.publishEvent(new ScheduleEvent(this, scheduleDefinition, ScheduleStatus.RUNNING));
                scheduleDefinition.getJob().run();
                applicationEventPublisher.publishEvent(new ScheduleEvent(this, scheduleDefinition, ScheduleStatus.SUCCESSFUL));
            }
        }, new CronTrigger(scheduleDefinition.getTimeExpression(), TimeZone.getTimeZone(scheduleDefinition.getTimeZone())));
        jobScheduledFutures.put(scheduleDefinition.getId(), future);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        scheduleTimeSchedules();
    }
}
