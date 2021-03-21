package com.nde.sch.controller;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ScheduleControllerTest {

    @Karate.Test
    Karate getAll() {
        return Karate.run("ScheduleDef").relativeTo(getClass());
    }
}