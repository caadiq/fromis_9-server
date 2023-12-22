package com.beemer.fromis9server.controller;

import com.beemer.fromis9server.dto.ScheduleDto;
import com.beemer.fromis9server.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fromis_9/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<ScheduleDto> getSchedulesByYearAndMonth(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        if (year != null && month != null) {
            return scheduleService.getSchedulesByYearAndMonth(year, month);
        }
        return scheduleService.getAllSchedules();
    }
}