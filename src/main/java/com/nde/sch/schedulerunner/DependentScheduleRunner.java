package com.nde.sch.schedulerunner;

import com.nde.sch.ScheduleEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DependentScheduleRunner {
    @EventListener
    public void onScheduleEvent(ScheduleEvent scheduleEvent) {
        System.out.println(scheduleEvent);
    }
}
