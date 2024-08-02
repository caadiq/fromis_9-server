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

    @Column(name = "color", nullable = false)
    val color: String,

    @OneToMany(mappedBy = "platform", cascade = [CascadeType.ALL])
    val schedules: List<Schedules>,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    val category: Category?
)
