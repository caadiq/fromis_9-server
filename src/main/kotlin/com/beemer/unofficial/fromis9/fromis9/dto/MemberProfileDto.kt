package com.beemer.unofficial.fromis9.fromis9.dto

import java.time.LocalDate

data class MemberProfileDto(
    val name: String,
    val birth: LocalDate,
    val profileImage: String,
    val position: String?,
    val instagram: String?
)