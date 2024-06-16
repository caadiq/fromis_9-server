package com.beemer.unofficial.fromis9.changelog.entity

import jakarta.persistence.*

@Entity
@Table(name = "Changelog")
data class Changelog(
    @Id
    @Column(name = "version", nullable = false)
    val version: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    val type: ChangelogType,

    @Column(name = "feature", nullable = false)
    val feature: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version")
    @MapsId
    val appVersion: AppVersion
)
