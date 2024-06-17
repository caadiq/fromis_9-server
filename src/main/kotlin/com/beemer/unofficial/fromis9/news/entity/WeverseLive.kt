package com.beemer.unofficial.fromis9.news.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "WeverseLive")
data class WeverseLive(
    @Id
    @Column(name = "live_id", nullable = false)
    val liveId: String,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "member", nullable = false)
    val member: String,

    @Column(name = "url", nullable = false)
    val url: String,

    @Column(name = "date", nullable = false)
    val date: LocalDateTime,
)