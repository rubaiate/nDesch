package com.nde.sch.context;

import com.nde.sch.definitions.DependentScheduleDefinition;
import com.nde.sch.definitions.ScheduleDefinition;
import com.nde.sch.definitions.TimedScheduleDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.batch.core.Job;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ScheduleContext {
    private Map<String, Job> jobs;
    private Map<String, ScheduleDefinition> schedules;
    private Map<String, TimedScheduleDefinition> timedSchedules;
    private Map<String, DependentScheduleDefinition> dependentSchedules;
}
