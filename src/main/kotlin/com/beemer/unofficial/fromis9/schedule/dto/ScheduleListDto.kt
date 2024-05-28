package com.beemer.unofficial.fromis9.schedule.dto

import java.time.LocalDateTime

data class ScheduleListDto(
    val scheduleId: Int,
    val playtform: String,
    val image: String,
    val dateTime: LocalDateTime,
    val schedule: String,
    val description: String?,
    val url: String?,
    val allDay: Boolean
)