package com.beemer.unofficial.fromis9.schedule.service

import com.beemer.unofficial.fromis9.common.dto.MessageDto
import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import com.beemer.unofficial.fromis9.schedule.dto.PlatformListDto
import com.beemer.unofficial.fromis9.schedule.dto.ScheduleDto
import com.beemer.unofficial.fromis9.schedule.dto.ScheduleListDto
import com.beemer.unofficial.fromis9.schedule.entity.Schedules
import com.beemer.unofficial.fromis9.schedule.repository.PlatformRepository
import com.beemer.unofficial.fromis9.schedule.repository.ScheduleRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ScheduleService(
    private val scheduleRepository: ScheduleRepository,
    private val platformRepository: PlatformRepository
) {
    @Value("\${admin.token}")
    private lateinit var adminToken: String

    @Transactional
    fun addSchedule(token: String, dto: ScheduleDto) : ResponseEntity<MessageDto> {
        if (token != adminToken) {
            throw CustomException(ErrorCode.UNAUTHORIZED)
        }

        val platform = platformRepository.findById(dto.platform)
            .orElseThrow { CustomException(ErrorCode.PLATFORM_NOT_FOUND) }

        val schedules = Schedules(
            platform,
            dto.dateTime,
            dto.schedule,
            dto.description,
            dto.url,
            dto.allDay
        )

        scheduleRepository.save(schedules)

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageDto("일정을 추가했습니다."))
    }

    @Transactional
    fun updateSchedule(scheduleId: Int, token: String, dto: ScheduleDto) : ResponseEntity<MessageDto> {
        if (token != adminToken) {
            throw CustomException(ErrorCode.UNAUTHORIZED)
        }

        val schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow { CustomException(ErrorCode.SCHEDULE_NOT_FOUND) }

        val platform = platformRepository.findById(dto.platform)
            .orElseThrow { CustomException(ErrorCode.PLATFORM_NOT_FOUND) }

        schedule.platform = platform
        schedule.date = dto.dateTime
        schedule.schedule = dto.schedule
        schedule.description = dto.description
        schedule.url = dto.url
        schedule.allDay = dto.allDay

        scheduleRepository.save(schedule)

        return ResponseEntity.status(HttpStatus.OK).body(MessageDto("일정을 수정했습니다."))
    }

    @Transactional
    fun deleteSchedule(scheduleId: Int, token: String) : ResponseEntity<MessageDto> {
        if (token != adminToken) {
            throw CustomException(ErrorCode.UNAUTHORIZED)
        }

        val schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow { CustomException(ErrorCode.SCHEDULE_NOT_FOUND) }

        scheduleRepository.delete(schedule)

        return ResponseEntity.status(HttpStatus.OK).body(MessageDto("일정을 삭제했습니다."))
    }

    fun getScheduleList(year: Int?, month: Int?, platform: List<String>?) : ResponseEntity<List<ScheduleListDto>> {
        val schedules = when {
            year == null && month == null -> scheduleRepository.findAll()
            year != null && month == null -> scheduleRepository.findByYear(year)
            year == null && month != null -> throw CustomException(ErrorCode.INVALID_PARAMETER)
            else -> scheduleRepository.findByYearAndMonth(year!!, month!!)
        }.filter {
            platform == null || platform.contains(it.platform.platform)
        }

        val scheduleList = schedules.map {
            ScheduleListDto(
                it.scheduleId,
                it.platform.platform,
                it.platform.image,
                it.platform.color,
                it.date,
                it.schedule,
                it.description,
                it.url,
                it.allDay
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(scheduleList)
    }

    fun getPlatformList() : ResponseEntity<List<PlatformListDto>> {
        val platforms = platformRepository.findAll()

        val platformList = platforms.map {
            PlatformListDto(
                it.platform,
                it.image,
                it.color
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(platformList)
    }
}