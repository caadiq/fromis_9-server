package com.beemer.unofficial.fromis9.news.dto

import java.time.LocalDateTime

data class WeverseLiveListDto(
    val liveId: String,
    val title: String,
    val members: String,
    val url: String,
    val date: LocalDateTime
)
