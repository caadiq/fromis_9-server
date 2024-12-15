package com.beemer.unofficial.fromis9.schedule.controller

import com.beemer.unofficial.fromis9.common.dto.MessageDto
import com.beemer.unofficial.fromis9.schedule.dto.PlatformListDto
import com.beemer.unofficial.fromis9.schedule.dto.ScheduleDetailsDto
import com.beemer.unofficial.fromis9.schedule.dto.ScheduleDto
import com.beemer.unofficial.fromis9.schedule.dto.ScheduleListDto
import com.beemer.unofficial.fromis9.schedule.service.ScheduleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ScheduleController(private val scheduleService: ScheduleService) {

    @PostMapping("/schedule")
    fun addSchedule(
        @RequestBody dto: ScheduleDto
    ) : ResponseEntity<MessageDto> {
        return scheduleService.addSchedule(dto)
    }

    @PutMapping("/schedule/{scheduleId}")
    fun updateSchedule(
        @PathVariable scheduleId: Int,
        @RequestBody dto: ScheduleDto
    ) : ResponseEntity<MessageDto> {
        return scheduleService.updateSchedule(scheduleId, dto)
    }

    @DeleteMapping("/schedule/{scheduleId}")
    fun deleteSchedule(
        @PathVariable scheduleId: Int
    ) : ResponseEntity<MessageDto> {
        return scheduleService.deleteSchedule(scheduleId)
    }

    @PostMapping("/schedules")
    fun getScheduleList(
        @RequestParam(required = false) year: Int?,
        @RequestParam(required = false) month: Int?,
        @RequestBody(required = false) category: List<String>
    ) : ResponseEntity<List<ScheduleDetailsDto>> {
        return scheduleService.getScheduleList(year, month, category)
    }

    @PostMapping("/schedules/search")
    fun getScheduleListBySchedule(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") limit: Int,
        @RequestParam query: String
    ) : ResponseEntity<ScheduleListDto> {
        return scheduleService.getScheduleListBySchedule(page, limit, query)
    }

    @GetMapping("/platforms")
    fun getPlatformList() : ResponseEntity<List<PlatformListDto>> {
        return scheduleService.getPlatformList()
    }

    @GetMapping("/categories")
    fun getCategoryList() : ResponseEntity<List<String>> {
        return scheduleService.getCategoryList()
    }
}