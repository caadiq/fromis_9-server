package com.beemer.unofficial.fromis9.schedule.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "Schedules")
data class Schedules(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false)
    val scheduleId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platform", nullable = false)
    val platform: Platforms,

    @Column(name = "date", nullable = false)
    val date: LocalDateTime,

    @Column(name = "schedule", nullable = false)
    val schedule: String,

    @Column(name = "description")
    val description: String?,

    @Column(name = "url")
    val url: String?
)
