package com.nde.sch.context;

import com.nde.sch.ScheduleEntity;
import com.nde.sch.ScheduleJob;
import com.nde.sch.definitions.DependentScheduleDefinition;
import com.nde.sch.definitions.ScheduleDefinition;
import com.nde.sch.definitions.TimedScheduleDefinition;
import com.nde.sch.enums.TriggerType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nde.sch.enums.ParameterKeys.*;

@Service
public class ScheduleContextLoader {
    private final ScheduleContext context;

    public ScheduleContextLoader(List<ScheduleEntity> scheduleEntities, List<ScheduleJob> jobList) {
        var jobs = jobList.stream().collect(Collectors.toMap(ScheduleJob::getName, job -> job));

        List<ScheduleEntity> timeScheduleEntities = filterSchedules(scheduleEntities, TriggerType.TIMED);
        var timedSchedules = createTimedSchedules(timeScheduleEntities, jobs);

        List<ScheduleEntity> dependentScheduleEntities = filterSchedules(scheduleEntities, TriggerType.DEPENDENT);
        var dependentSchedules = createDependentSchedules(dependentScheduleEntities, jobs);

        var schedules = new HashMap<String, ScheduleDefinition>();
        schedules.putAll(timedSchedules);
        schedules.putAll(dependentSchedules);
        context = new ScheduleContext(jobs, schedules, timedSchedules, dependentSchedules);
    }

    private List<ScheduleEntity> filterSchedules(List<ScheduleEntity> scheduleEntities, TriggerType triggerType) {
        return scheduleEntities.stream()
                .filter(entity -> entity.getTriggerType() == triggerType)
                .collect(Collectors.toList());
    }

    @Bean
    public ScheduleContext context() {
        return context;
    }

    private Map<String, TimedScheduleDefinition> createTimedSchedules(List<ScheduleEntity> scheduleEntities, Map<String, ScheduleJob> jobs) {
        return scheduleEntities.stream()
                .map(entity -> new TimedScheduleDefinition(entity,
                        jobs.get(entity.getJobName()),
                        entity.getParameters().get(SCHEDULE_EXPRESSION.toString()),
                        entity.getParameters().get(TIME_ZONE.toString())))
                .collect(Collectors.toMap(ScheduleDefinition::getId, def -> def));
    }

    private Map<String, DependentScheduleDefinition> createDependentSchedules(List<ScheduleEntity> scheduleEntities, Map<String, ScheduleJob> jobs) {
        return scheduleEntities.stream()
                .map(entity -> new DependentScheduleDefinition(entity,
                        jobs.get(entity.getJobName()),
                        entity.getParameters().get(DEPENDENT_NAME.toString())))
                .collect(Collectors.toMap(ScheduleDefinition::getId, def -> def));
    }

}
