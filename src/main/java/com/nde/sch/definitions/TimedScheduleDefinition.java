package com.nde.sch.definitions;

import com.nde.sch.ScheduleEntity;
import com.nde.sch.ScheduleJob;
import lombok.Getter;

@Getter
public class TimedScheduleDefinition extends ScheduleDefinition {
    private final String timeExpression;
    private final String timeZone;

    public TimedScheduleDefinition(ScheduleEntity scheduleEntity, ScheduleJob job, String timeExpression, String timeZone) {
        super(scheduleEntity, job);
        this.timeExpression = timeExpression;
        this.timeZone = timeZone;
    }
}
