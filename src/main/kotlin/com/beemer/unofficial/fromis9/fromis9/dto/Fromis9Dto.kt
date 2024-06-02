package com.beemer.unofficial.fromis9.fromis9.dto

data class Fromis9Dto(
    val bannerImage: String,
    val detail: String,
    val debut: String,
    val members: List<Member>,
    val socials: List<Social>,
    val albums: List<Albums>
)

data class Member(
    val name: String,
    val profileImage: String
)

data class Social(
    val sns: String,
    val url: String,
)

data class Albums(
    val albumName: String,
    val cover: String,
    val colorMain: String,
    val colorPrimary: String,
    val colorSecondary: String
)