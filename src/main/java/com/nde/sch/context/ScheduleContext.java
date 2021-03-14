package com.nde.sch.context;

import com.nde.sch.ScheduleJob;
import com.nde.sch.definitions.DependentScheduleDefinition;
import com.nde.sch.definitions.ScheduleDefinition;
import com.nde.sch.definitions.TimedScheduleDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class ScheduleContext {
    private Map<String, ScheduleJob> jobs;
    private Map<String, ScheduleDefinition> schedules;
    private Map<String, TimedScheduleDefinition> timedSchedules;
    private Map<String, DependentScheduleDefinition> dependentSchedules;
    private Map<String, List<DependentScheduleDefinition>> dependencyMap;
}
