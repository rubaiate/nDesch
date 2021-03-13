package com.nde.sch;

import com.nde.sch.enums.ScheduleAction;
import com.nde.sch.enums.TriggerType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ScheduleEntity {
    String id;
    String jobName;
    TriggerType triggerType;
    Map<String,String> parameters;
    Boolean enabled;
    ScheduleAction action;
}
