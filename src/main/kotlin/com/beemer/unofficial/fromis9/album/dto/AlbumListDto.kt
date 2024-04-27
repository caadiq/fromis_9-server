package com.beemer.unofficial.fromis9.album.dto

data class AlbumListDto(
    val albumName: String,
    val type: String,
    val cover: String,
    val release: String,
    val colorMain: String,
    val colorPrimary: String,
    val colorSecondary: String
)