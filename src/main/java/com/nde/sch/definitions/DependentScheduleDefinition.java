package com.nde.sch.definitions;

import com.nde.sch.ScheduleEntity;
import com.nde.sch.ScheduleJob;
import lombok.Getter;

@Getter
public class DependentScheduleDefinition extends ScheduleDefinition {
    private String dependentScheduleId;

    public DependentScheduleDefinition(ScheduleEntity scheduleEntity, ScheduleJob job, String dependentScheduleId) {
        super(scheduleEntity, job);
        this.dependentScheduleId = dependentScheduleId;
    }
}
