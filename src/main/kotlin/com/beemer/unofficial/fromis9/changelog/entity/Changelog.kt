package com.beemer.unofficial.fromis9.changelog.entity

import jakarta.persistence.*

@Entity
@Table(name = "Changelog")
data class Changelog(
    @Id
    @Column(name = "feature_id", nullable = false)
    val featureId: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version")
    val appVersion: AppVersion,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    val type: ChangelogType,

    @Column(name = "feature", nullable = false)
    val feature: String,
)
