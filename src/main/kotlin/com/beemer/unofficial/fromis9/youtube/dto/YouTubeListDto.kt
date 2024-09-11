package com.beemer.unofficial.fromis9.youtube.dto

import java.time.LocalDateTime

data class YouTubeListDto(
    val page: YouTubePageDto,
    val videos: List<YouTubeDto>
)

data class YouTubePageDto(
    val previousPage: Int?,
    val currentPage: Int,
    val nextPage: Int?,
)

data class YouTubeDto(
    val videoId: String,
    val title: String,
    val thumbnail: String,
    val publishedAt: LocalDateTime,
    val isNew: Boolean,
    val description: String,
    val length: Int?,
    val views: Int?
)