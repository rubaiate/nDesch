package com.nde.sch.service;

import com.nde.sch.ScheduleEvent;
import com.nde.sch.ScheduleStatus;
import com.nde.sch.context.ScheduleContext;
import com.nde.sch.definitions.ScheduleDefinition;
import com.nde.sch.dto.ScheduleDefinitionDto;
import com.nde.sch.enums.ScheduleRunStatus;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nde.sch.enums.ParameterKeys.*;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleContext scheduleContext;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final Map<String, ScheduleStatus> scheduleStatusMap = new HashMap<>();

    public void run(ScheduleDefinition scheduleDefinition) {
        updateStatus(scheduleDefinition, ScheduleRunStatus.RUNNING);

        scheduleDefinition.getJob().run();

        updateStatus(scheduleDefinition, ScheduleRunStatus.SUCCESSFUL);
    }

    private void updateStatus(ScheduleDefinition scheduleDefinition, ScheduleRunStatus running) {
        ScheduleStatus scheduleStart = new ScheduleStatus(scheduleDefinition, running);
        scheduleStatusMap.put(scheduleDefinition.getId(), scheduleStart);
        applicationEventPublisher.publishEvent(new ScheduleEvent(this, scheduleStart));
    }

    public void run(String jobId) {
        var def = scheduleContext.getTimedSchedules().get(jobId);
        if (def != null) {
            run(def);
        }
    }

    public ScheduleStatus getStatus(String id) {
        return scheduleStatusMap.get(id);
    }

    public List<ScheduleDefinitionDto> getAllDefinitions() {
        return scheduleContext.getSchedules().values().stream()
                .map(ScheduleDefinition::getScheduleEntity)
                .map(entity -> new ScheduleDefinitionDto(entity.getId(),
                        entity.getJobName(),
                        entity.getEnabled(),
                        entity.getAction(),
                        entity.getTriggerType(),
                        entity.getParameters().get(SCHEDULE_EXPRESSION.toString()),
                        entity.getParameters().get(TIME_ZONE.toString()),
                        entity.getParameters().get(DEPENDENT_NAME.toString())))
                .collect(Collectors.toUnmodifiableList());
    }
}
