package com.nde.sch;

import com.nde.sch.definitions.ScheduleDefinition;
import com.nde.sch.enums.ScheduleStatus;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
public class ScheduleEvent extends ApplicationEvent {
    private final ScheduleDefinition scheduleDefinition;
    private final ScheduleStatus scheduleStatus;

    public ScheduleEvent(Object source, ScheduleDefinition scheduleDefinition, ScheduleStatus scheduleStatus) {
        super(source);
        this.scheduleDefinition = scheduleDefinition;
        this.scheduleStatus = scheduleStatus;
    }

}
