package com.nde.sch.controller;

import com.nde.sch.dto.ScheduleDefinitionDto;
import com.nde.sch.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("scheduleDefs")
public class ScheduleController {
    private final ScheduleService scheduleService;

    ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ScheduleDefinitionDto>> getAll() {
        return new ResponseEntity<>(scheduleService.getAllDefinitions(), HttpStatus.OK);
    }
}
