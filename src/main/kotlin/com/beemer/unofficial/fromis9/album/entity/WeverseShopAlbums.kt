package com.beemer.unofficial.fromis9.album.entity

import jakarta.persistence.*

@Entity
@Table(name = "WeverseShopAlbums")
data class WeverseShopAlbums(
    @Id
    @Column(name = "album_id", nullable = false)
    val albumId: Int,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "image", nullable = false)
    val image: String,

    @Column(name = "url", nullable = false)
    val url: String,

    @Column(name = "price", nullable = false)
    val price: Int,

    @Column(name = "sold_out", nullable = false)
    val soldOut: Boolean,
)