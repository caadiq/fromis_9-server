package com.beemer.unofficial.fromis9.album.dto

data class SongDetailsDto(
    val lyricist: String,
    val composer: String,
    val arranger: String?,
    val lyrics: String,
    val fanchant: String?,
    val videoId: String,
    val fanchantVideoId: String?
)
