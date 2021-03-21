package com.nde.sch;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
public class ScheduleEvent extends ApplicationEvent {
    private final ScheduleStatus scheduleStatus;

    public ScheduleEvent(Object source, ScheduleStatus scheduleStatus) {
        super(source);
        this.scheduleStatus = scheduleStatus;
    }

}
