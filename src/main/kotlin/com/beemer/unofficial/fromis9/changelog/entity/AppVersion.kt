package com.beemer.unofficial.fromis9.changelog.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "AppVersion")
data class AppVersion(
    @Id
    @Column(name = "version", nullable = false)
    val version: String,

    @Column(name = "release", nullable = false)
    val date: LocalDate,

    @Column(name = "apk")
    val apk: String,

    @OneToMany(mappedBy = "appVersion", cascade = [CascadeType.ALL])
    val changelogs: List<Changelog>
)
