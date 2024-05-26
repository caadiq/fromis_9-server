package com.beemer.unofficial.fromis9.schedule.service

import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import com.beemer.unofficial.fromis9.schedule.dto.ScheduleListDto
import com.beemer.unofficial.fromis9.schedule.repository.ScheduleRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ScheduleService(
    private val scheduleRepository: ScheduleRepository
) {
    fun getScheduleList(year: Int?, month: Int?) : ResponseEntity<List<ScheduleListDto>> {
        val schedules = when {
            year == null && month == null -> scheduleRepository.findAll()
            year != null && month == null -> scheduleRepository.findByYear(year)
            year == null && month != null -> throw CustomException(ErrorCode.INVALID_PARAMETER)
            else -> scheduleRepository.findByYearAndMonth(year!!, month!!)
        }

        val scheduleList = schedules.map {
            ScheduleListDto(
                it.platform.image,
                it.date.toString(),
                it.schedule,
                it.description,
                it.url
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(scheduleList)
    }
}