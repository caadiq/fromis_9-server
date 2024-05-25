package com.beemer.unofficial.fromis9.youtube.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "YouTubeVideos")
data class YouTubeVideos(
    @Id
    @Column(name = "video_id", nullable = false)
    val videoId: String,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "thumbnail", nullable = false)
    val thumbnail: String,

    @Column(name = "published_at", nullable = false)
    val publishedAt: LocalDateTime,

    @Column(name = "description", nullable = false)
    val description: String,

    @OneToOne(mappedBy = "video", cascade = [CascadeType.ALL])
    var details: YouTubeVideoDetails? = null
)
