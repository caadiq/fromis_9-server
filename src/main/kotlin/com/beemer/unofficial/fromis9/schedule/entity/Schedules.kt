package com.beemer.unofficial.fromis9.schedule.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "Schedules")
data class Schedules(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platform", nullable = false)
    var platform: Platforms,

    @Column(name = "date", nullable = false)
    var date: LocalDateTime,

    @Column(name = "schedule", nullable = false)
    var schedule: String,

    @Column(name = "description")
    var description: String?,

    @Column(name = "url")
    var url: String?,

    @Column(name = "all_day", nullable = false)
    var allDay: Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false)
    val scheduleId: Int = 0
}