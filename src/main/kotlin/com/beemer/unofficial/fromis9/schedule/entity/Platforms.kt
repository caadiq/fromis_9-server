package com.beemer.unofficial.fromis9.schedule.entity

import jakarta.persistence.*

@Entity
@Table(name = "Platforms")
data class Platforms(
    @Id
    @Column(name = "platform", nullable = false)
    val platform: String,

    @Column(name = "image", nullable = false)
    val image: String,

    @OneToMany(mappedBy = "platform", cascade = [CascadeType.ALL])
    val schedules: List<Schedules>
)
