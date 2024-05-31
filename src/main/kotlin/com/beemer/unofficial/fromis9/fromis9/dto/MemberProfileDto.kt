package com.beemer.unofficial.fromis9.fromis9.dto

data class MemberProfileDto(
    val name: String,
    val birth: String,
    val profileImage: String,
    val position: String?,
    val instagram: String?
)