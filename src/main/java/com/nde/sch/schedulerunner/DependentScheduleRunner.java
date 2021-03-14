package com.nde.sch.schedulerunner;

import com.nde.sch.ScheduleEvent;
import com.nde.sch.ScheduleService;
import com.nde.sch.context.ScheduleContext;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class DependentScheduleRunner {
    private final ScheduleContext scheduleContext;
    private final ScheduleService scheduleService;

    @EventListener
    public void onScheduleEvent(ScheduleEvent scheduleEvent) {
        System.out.println(scheduleEvent);

        var dependentScheduleDefinitions = scheduleContext.getDependencyMap()
                .getOrDefault(scheduleEvent.getScheduleDefinition().getId(), Collections.emptyList());

        dependentScheduleDefinitions.forEach(scheduleService::run);
    }
}
