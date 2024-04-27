package com.beemer.unofficial.fromis9.album.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "Albums")
data class Albums(
    @Id
    @Column(name = "album_name", nullable = false)
    val albumName: String,

    @Column(name = "type", nullable = false)
    val type: String,

    @Column(name = "cover", nullable = false)
    val cover: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "release", nullable = false)
    val release: LocalDate,

    @Column(name = "color_main", nullable = false)
    val colorMain: String,

    @Column(name = "color_primary", nullable = false)
    val colorPrimary: String,

    @Column(name = "color_secondary", nullable = false)
    val colorSecondary: String,

    @OneToMany(mappedBy = "albumName", cascade = [CascadeType.ALL])
    val songs: List<Songs>,

    @OneToMany(mappedBy = "albumName", cascade = [CascadeType.ALL])
    val photos: List<Photos>
)