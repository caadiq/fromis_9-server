package com.beemer.unofficial.fromis9.changelog.dto

import java.time.LocalDate

data class ChangelogListDto(
    val version: String,
    val date: LocalDate,
    val changelog: List<Changelog>
)

data class Changelog(
    val icon: String,
    val type: String,
    val feature: String
)
