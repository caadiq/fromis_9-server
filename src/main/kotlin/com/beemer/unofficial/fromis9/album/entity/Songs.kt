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

    @Column(name = "lyrics")
    val lyrics: String?,

    @Column(name = "fanchant")
    val fanchant: String?,

    @Column(name = "length", nullable = false)
    val length: String,

    @Column(name = "title", nullable = false)
    val title: Boolean,

    @Column(name = "track", nullable = false)
    val track: Int,

    @Column(name = "video_id")
    val videoId: String?,

    @Column(name = "fanchant_video_id")
    val fanchantVideoId: String?
)