package com.beemer.unofficial.fromis9.youtube.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
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

    @Column(name = "length")
    var length: Int?,

    @Column(name = "views")
    var views: Int?
)
