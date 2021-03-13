package com.nde.sch.definitions;

import com.nde.sch.ScheduleEntity;
import lombok.Getter;
import org.springframework.batch.core.Job;

@Getter
public class TimedScheduleDefinition extends ScheduleDefinition {
    private final String timeExpression;
    private final String timeZone;

    public TimedScheduleDefinition(ScheduleEntity scheduleEntity, Job job, String timeExpression, String timeZone) {
        super(scheduleEntity, job);
        this.timeExpression = timeExpression;
        this.timeZone = timeZone;
    }
}
