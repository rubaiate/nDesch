package com.nde.sch.definitions;

import com.nde.sch.ScheduleEntity;
import com.nde.sch.ScheduleJob;
import lombok.ToString;

@ToString
public class ScheduleDefinition {
    private final ScheduleEntity scheduleEntity;
    private final ScheduleJob job;

    public ScheduleDefinition(ScheduleEntity scheduleEntity, ScheduleJob job) {
        this.scheduleEntity = scheduleEntity;
        this.job = job;
    }

    public String getId() {
        return scheduleEntity.getId();
    }

    public String getJobName() {
        return scheduleEntity.getJobName();
    }

    public ScheduleJob getJob() {
        return job;
    }
}
