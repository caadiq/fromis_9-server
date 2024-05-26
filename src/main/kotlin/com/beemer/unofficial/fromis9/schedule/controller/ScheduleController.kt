package com.beemer.unofficial.fromis9.schedule.controller

import com.beemer.unofficial.fromis9.schedule.dto.ScheduleListDto
import com.beemer.unofficial.fromis9.schedule.service.ScheduleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/fromis9/schedule")
class ScheduleController(private val scheduleService: ScheduleService) {

    @GetMapping("/list")
    fun getScheduleList(
        @RequestParam(required = false) year: Int?,
        @RequestParam(required = false) month: Int?
    ) : ResponseEntity<List<ScheduleListDto>> {
        return scheduleService.getScheduleList(year, month)
    }
}