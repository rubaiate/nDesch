package com.nde.sch.definitions;

import com.nde.sch.ScheduleEntity;
import lombok.Getter;
import org.springframework.batch.core.Job;

@Getter
public class DependentScheduleDefinition extends ScheduleDefinition{
    private String dependentScheduleId;

    public DependentScheduleDefinition(ScheduleEntity scheduleEntity, Job job, String dependentScheduleId) {
        super(scheduleEntity, job);
        this.dependentScheduleId = dependentScheduleId;
    }
}
