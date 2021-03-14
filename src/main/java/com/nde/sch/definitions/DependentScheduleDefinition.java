package com.nde.sch.definitions;

import com.nde.sch.ScheduleEntity;
import com.nde.sch.ScheduleJob;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DependentScheduleDefinition extends ScheduleDefinition {
    private final String dependentScheduleId;

    public DependentScheduleDefinition(ScheduleEntity scheduleEntity, ScheduleJob job, String dependentScheduleId) {
        super(scheduleEntity, job);
        this.dependentScheduleId = dependentScheduleId;
    }
}
