package com.beemer.unofficial.fromis9.schedule.dto

data class ScheduleListDto(
    val image: String,
    val date: String,
    val schedule: String,
    val description: String?,
    val url: String?
)