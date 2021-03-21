package com.nde.sch.dto;

import com.nde.sch.enums.ScheduleAction;
import com.nde.sch.enums.TriggerType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScheduleDefinitionDto {
    String id;
    String jobName;
    Boolean enabled;
    ScheduleAction action;
    TriggerType triggerType;
    String scheduleExpression;
    String timeZone;
    String dependentName;
}
