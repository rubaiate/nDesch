package com.nde.sch;

import com.nde.sch.context.ScheduleContext;
import com.nde.sch.definitions.ScheduleDefinition;
import com.nde.sch.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleContext scheduleContext;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void run(ScheduleDefinition scheduleDefinition) {
        applicationEventPublisher.publishEvent(new ScheduleEvent(this, scheduleDefinition, ScheduleStatus.RUNNING));
        scheduleDefinition.getJob().run();
        applicationEventPublisher.publishEvent(new ScheduleEvent(this, scheduleDefinition, ScheduleStatus.SUCCESSFUL));
    }

    public void run(String jobId) {
        var def = scheduleContext.getTimedSchedules().get(jobId);
        if (def != null) {
            run(def);
        }
    }
}
