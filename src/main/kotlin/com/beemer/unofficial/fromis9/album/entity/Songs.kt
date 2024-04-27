package com.beemer.unofficial.fromis9.album.entity

import jakarta.persistence.*

@Entity
@Table(name = "Songs")
data class Songs(
    @Id
    @Column(name = "song_name", nullable = false)
    val songName: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_name", nullable = false)
    val albumName: Albums,

    @Column(name = "lyricist", nullable = false)
    val lyricist: String,

    @Column(name = "composer", nullable = false)
    val composer: String,

    @Column(name = "arranger")
    val arranger: String?,

    @Column(name = "lyrics", nullable = false)
    val lyrics: String,

    @Column(name = "length", nullable = false)
    val length: String,

    @Column(name = "title", nullable = false)
    val title: Boolean,

    @Column(name = "track", nullable = false)
    val track: Int,

    @Column(name = "video_id", nullable = false)
    val videoId: String
)