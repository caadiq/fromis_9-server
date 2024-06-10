package com.beemer.unofficial.fromis9.news.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

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
    val date: LocalDate
)