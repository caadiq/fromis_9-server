package com.beemer.unofficial.fromis9.news.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "WeverseNotice")
data class WeverseNotice(
    @Id
    @Column(name = "notice_id", nullable = false)
    val noticeId: Int,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "url", nullable = false)
    val url: String,

    @Column(name = "date", nullable = false)
    val date: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portal", nullable = false)
    val portal: Portals
)