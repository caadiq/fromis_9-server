package com.beemer.unofficial.fromis9.news.dto

import java.time.LocalDateTime

data class LatestNewsListDto(
    val id: Int,
    val title: String,
    val url: String,
    val date: LocalDateTime,
    val portal: String,
    val portalImage: String
)