package com.beemer.unofficial.fromis9.album.dto

data class SongListDto(
    val songName: String,
    val albumName: String,
    val albumCover: String,
    val colorMain: String,
    val colorPrimary: String,
    val colorSecondary: String,
    val titleTrack: Boolean
)
