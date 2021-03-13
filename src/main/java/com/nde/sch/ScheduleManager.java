package com.nde.sch;

import com.nde.sch.context.ScheduleContext;
import com.nde.sch.definitions.TimedScheduleDefinition;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.TimeZone;
import java.util.TimerTask;

@Service
public class ScheduleManager implements ApplicationListener<ApplicationReadyEvent> {
    private final ScheduleContext scheduleContext;
    private final JobLauncher jobLauncher;
    private final ThreadPoolTaskScheduler taskScheduler;

    public ScheduleManager(ScheduleContext scheduleContext, JobLauncher jobLauncher, ThreadPoolTaskScheduler taskScheduler) {
        this.scheduleContext = scheduleContext;
        this.jobLauncher = jobLauncher;
        this.taskScheduler = taskScheduler;
    }

    private void scheduleTimeSchedules() {
        scheduleContext.getTimedSchedules().values().forEach(this::schedule);
    }

    private void schedule(TimedScheduleDefinition scheduleDefinition) {
        taskScheduler.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    jobLauncher.run(scheduleDefinition.getJob(), new JobParameters());
                } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
                    //TODO handle exception properly
                    e.printStackTrace();
                }
            }
        }, new CronTrigger(scheduleDefinition.getTimeExpression(), TimeZone.getTimeZone(scheduleDefinition.getTimeZone())));
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        scheduleTimeSchedules();
    }
}
