package com.beemer.unofficial.fromis9.schedule.service

import com.beemer.unofficial.fromis9.common.dto.MessageDto
import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import com.beemer.unofficial.fromis9.schedule.dto.*
import com.beemer.unofficial.fromis9.schedule.entity.Schedules
import com.beemer.unofficial.fromis9.schedule.repository.CategoryRepository
import com.beemer.unofficial.fromis9.schedule.repository.PlatformRepository
import com.beemer.unofficial.fromis9.schedule.repository.ScheduleRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ScheduleService(
    private val scheduleRepository: ScheduleRepository,
    private val platformRepository: PlatformRepository,
    private val categoryRepository: CategoryRepository
) {
    @Transactional
    fun addSchedule(dto: ScheduleDto) : ResponseEntity<MessageDto> {
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
    fun updateSchedule(scheduleId: Int, dto: ScheduleDto) : ResponseEntity<MessageDto> {
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
    fun deleteSchedule(scheduleId: Int) : ResponseEntity<MessageDto> {
        val schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow { CustomException(ErrorCode.SCHEDULE_NOT_FOUND) }

        scheduleRepository.delete(schedule)

        return ResponseEntity.status(HttpStatus.OK).body(MessageDto("일정을 삭제했습니다."))
    }

    fun getScheduleList(year: Int?, month: Int?, category: List<String>) : ResponseEntity<List<ScheduleDetailsDto>> {
        val schedules = when {
            year == null && month == null -> scheduleRepository.findAll()
            year != null && month == null -> scheduleRepository.findByYear(year)
            year == null && month != null -> throw CustomException(ErrorCode.INVALID_PARAMETER)
            else -> scheduleRepository.findByYearAndMonth(year!!, month!!)
        }.filter {
            category.isEmpty() || category.contains(it.platform.category?.category)
        }

        val scheduleList = schedules.map {
            ScheduleDetailsDto(
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

    fun getScheduleListBySchedule(page: Int, limit: Int, query: String) : ResponseEntity<ScheduleListDto> {
        val limitAdjusted = 1.coerceAtLeast(50.coerceAtMost(limit))
        val pageable = PageRequest.of(page, limitAdjusted, Sort.by("date").descending())
        
        val schedules = scheduleRepository.findByScheduleAndDescription(pageable, query)

        if (schedules.content.isEmpty() && schedules.totalElements > 0) {
            throw CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        }

        val prevPage = if (schedules.hasPrevious()) schedules.number - 1 else null
        val currentPage = schedules.number
        val nextPage = if (schedules.hasNext()) schedules.number + 1 else null

        val pages = SchedulePageDto(prevPage, currentPage, nextPage)

        val scheduleList = schedules.content.map {
            ScheduleDetailsDto(
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

        return ResponseEntity.status(HttpStatus.OK).body(ScheduleListDto(pages, scheduleList))
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

    fun getCategoryList() : ResponseEntity<List<String>> {
        val categories = categoryRepository.findAll()

        val categoryList = categories.map {
            it.category
        }

        return ResponseEntity.status(HttpStatus.OK).body(categoryList)
    }
}