package com.beemer.unofficial.fromis9.fromis9.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "BannerImages")
data class BannerImages(
    @Id
    @Column(name = "imageUrl", nullable = false)
    val imageUrl: String
)
