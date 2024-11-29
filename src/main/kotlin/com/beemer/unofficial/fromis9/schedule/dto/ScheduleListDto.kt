package com.beemer.unofficial.fromis9.schedule.dto

import java.time.LocalDateTime

data class ScheduleListDto(
    val page: SchedulePageDto,
    val schedules: List<ScheduleDetailsDto>
)

data class SchedulePageDto(
    val previousPage: Int?,
    val currentPage: Int,
    val nextPage: Int?,
)

data class ScheduleDetailsDto(
    val scheduleId: Int,
    val platform: String,
    val image: String,
    val color: String,
    val dateTime: LocalDateTime,
    val schedule: String,
    val description: String?,
    val url: String?,
    val allDay: Boolean
)