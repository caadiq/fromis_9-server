package com.beemer.unofficial.fromis9.album.entity

import jakarta.persistence.*

@Entity
@Table(name = "Photos")
data class Photos(
    @Id
    @Column(name = "photo_id", nullable = false)
    val photoId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_name", nullable = false)
    val albumName: Albums,

    @Column(name = "photo", nullable = false)
    val photo: String,

    @Column(name = "concept")
    val concept: String?
)