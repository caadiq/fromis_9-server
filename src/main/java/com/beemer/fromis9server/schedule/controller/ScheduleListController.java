package com.beemer.fromis9server.schedule.controller;

import com.beemer.fromis9server.schedule.service.ScheduleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fromis9")
public class ScheduleListController {
    private final ScheduleListService scheduleListService;

    @Autowired
    public ScheduleListController(ScheduleListService scheduleListService) {
        this.scheduleListService = scheduleListService;
    }

    @GetMapping("/schedulelist")
    public ResponseEntity<?> getScheduleList(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        if (year == null && month == null) {
            return ResponseEntity.badRequest().body("'year', 'month' 파라미터가 필요합니다.");
        } else if (year == null) {
            return ResponseEntity.badRequest().body("'year' 파라미터가 필요합니다.");
        } else if (month == null) {
            return ResponseEntity.badRequest().body("'month' 파라미터가 필요합니다.");
        }
        return ResponseEntity.ok(scheduleListService.getScheduleList(year, month));
    }
}
