package com.nde.sch;

import com.nde.sch.definitions.ScheduleDefinition;
import com.nde.sch.enums.ScheduleRunStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScheduleStatus {
    private final ScheduleDefinition scheduleDefinition;
    private final ScheduleRunStatus runStatus;

    public String getId() {
        return scheduleDefinition.getId();
    }

}
