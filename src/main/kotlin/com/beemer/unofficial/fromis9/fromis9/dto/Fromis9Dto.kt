package com.beemer.unofficial.fromis9.fromis9.dto

import com.beemer.unofficial.fromis9.album.dto.AlbumListDto
import java.time.LocalDateTime

data class Fromis9Dto(
    val bannerImage: String,
    val debut: String,
    val members: List<Member>,
    val socials: List<Social>,
    val albums: List<AlbumListDto>,
    val latestNews: List<LatestNews>
)

data class Member(
    val name: String,
    val profileImage: String
)

data class Social(
    val sns: String,
    val url: String,
)

data class LatestNews(
    val id: Int,
    val title: String,
    val url: String,
    val date: LocalDateTime
)