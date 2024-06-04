package com.beemer.unofficial.fromis9.album.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class WeverseShopAlbumListDto(
    val index: Int,
    val title: String,
    @JsonProperty("img_src") val imgSrc: String,
    val url: String,
    val price: Int,
    @JsonProperty("is_sold_out") val isSoldOut: Boolean
)
