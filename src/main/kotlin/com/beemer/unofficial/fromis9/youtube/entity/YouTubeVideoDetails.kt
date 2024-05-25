package com.beemer.unofficial.fromis9.youtube.entity

import jakarta.persistence.*

@Entity
@Table(name = "YouTubeVideoDetails")
data class YouTubeVideoDetails(
    @Id
    @Column(name = "video_id", nullable = false)
    val videoId: String,

    @Column(name = "length", nullable = false)
    val length: Int,

    @Column(name = "views", nullable = false)
    val views: Int,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    @MapsId
    val video: YouTubeVideos
)
