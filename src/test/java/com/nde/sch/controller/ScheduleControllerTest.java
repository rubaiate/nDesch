package com.nde.sch.controller;

import com.intuit.karate.junit5.Karate;
import com.nde.sch.dependentschedule.DependentScheduleTestComponent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(DependentScheduleTestComponent.class)
class ScheduleControllerTest {

    @Karate.Test
    Karate getAll() {
        return Karate.run("ScheduleDef").relativeTo(getClass());
    }
}