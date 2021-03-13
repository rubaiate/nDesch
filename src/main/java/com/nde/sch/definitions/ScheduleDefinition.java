package com.nde.sch.definitions;

import com.nde.sch.ScheduleEntity;
import org.springframework.batch.core.Job;

public class ScheduleDefinition {
    private ScheduleEntity scheduleEntity;
    private Job job;
    public ScheduleDefinition(ScheduleEntity scheduleEntity, Job job) {
        this.scheduleEntity = scheduleEntity;
        this.job = job;
    }

    public String getId() {
        return scheduleEntity.getId();
    }

    public String getJobName() {
        return scheduleEntity.getJobName();
    }

    public Job getJob() {
        return job;
    }
}
