package com.beemer.unofficial.fromis9.album.dto

data class SongDetailsDto(
    val lyricist: String,
    val composer: String,
    val arranger: String?,
    val lyrics: String,
    val videoId: String,
    val colorPrimary: String,
    val colorSecondary: String
)
