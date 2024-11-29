package com.beemer.unofficial.fromis9.schedule.entity

import jakarta.persistence.*

@Entity
@Table(name = "Category")
data class Category(
    @Id
    @Column(name = "category", nullable = false)
    val category: String,

    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL])
    val platforms: List<Platforms>
)
