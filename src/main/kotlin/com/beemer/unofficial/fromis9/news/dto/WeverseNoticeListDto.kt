package com.beemer.unofficial.fromis9.news.dto

import java.time.LocalDateTime

data class WeverseNoticeListDto(
    val noticeId: Int,
    val title: String,
    val url: String,
    val date: LocalDateTime
)
