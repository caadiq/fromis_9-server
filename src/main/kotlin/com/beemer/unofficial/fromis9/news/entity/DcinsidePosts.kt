package com.beemer.unofficial.fromis9.news.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "DcinsidePosts")
data class DcinsidePosts(
    @Id
    @Column(name = "post_id", nullable = false)
    val liveId: Int,

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