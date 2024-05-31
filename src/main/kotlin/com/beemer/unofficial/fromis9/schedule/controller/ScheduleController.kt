package com.beemer.unofficial.fromis9.schedule.controller

import com.beemer.unofficial.fromis9.common.dto.MessageDto
import com.beemer.unofficial.fromis9.schedule.dto.PlatformListDto
import com.beemer.unofficial.fromis9.schedule.dto.ScheduleDto
import com.beemer.unofficial.fromis9.schedule.dto.ScheduleListDto
import com.beemer.unofficial.fromis9.schedule.service.ScheduleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/fromis9/")
class ScheduleController(private val scheduleService: ScheduleService) {

    @PostMapping("/schedule")
    fun addSchedule(
        @RequestHeader(value = "Authorization") authorization: String,
        @RequestBody dto: ScheduleDto
    ) : ResponseEntity<MessageDto> {
        return scheduleService.addSchedule(authorization, dto)
    }

    @PutMapping("/schedule/{scheduleId}")
    fun updateSchedule(
        @PathVariable scheduleId: Int,
        @RequestHeader(value = "Authorization") authorization: String,
        @RequestBody dto: ScheduleDto
    ) : ResponseEntity<MessageDto> {
        return scheduleService.updateSchedule(scheduleId, authorization, dto)
    }

    @DeleteMapping("/schedule/{scheduleId}")
    fun deleteSchedule(
        @PathVariable scheduleId: Int,
        @RequestHeader(value = "Authorization") authorization: String
    ) : ResponseEntity<MessageDto> {
        return scheduleService.deleteSchedule(scheduleId, authorization)
    }

    @GetMapping("/schedules")
    fun getScheduleList(
        @RequestParam(required = false) year: Int?,
        @RequestParam(required = false) month: Int?
    ) : ResponseEntity<List<ScheduleListDto>> {
        return scheduleService.getScheduleList(year, month)
    }

    @GetMapping("/platforms")
    fun getPlatformList() : ResponseEntity<List<PlatformListDto>> {
        return scheduleService.getPlatformList()
    }
}