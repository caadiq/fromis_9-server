package com.beemer.unofficial.fromis9.schedule.dto

import java.time.LocalDateTime

data class ScheduleDto(
    val platform: String,
    val dateTime: LocalDateTime,
    val schedule: String,
    val description: String?,
    val url: String?,
    val allDay: Boolean
)