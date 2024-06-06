package com.beemer.unofficial.fromis9.fromis9.dto

import com.beemer.unofficial.fromis9.album.dto.AlbumListDto

data class Fromis9Dto(
    val bannerImage: String,
    val debut: String,
    val members: List<Member>,
    val socials: List<Social>,
    val albums: List<AlbumListDto>
)

data class Member(
    val name: String,
    val profileImage: String
)

data class Social(
    val sns: String,
    val url: String,
)