package com.beemer.unofficial.fromis9.changelog.entity

import jakarta.persistence.*

@Entity
@Table(name = "ChangelogType")
data class ChangelogType(
    @Id
    @Column(name = "type", nullable = false)
    val type: String,

    @Column(name = "icon", nullable = false)
    val icon: String,

    @OneToMany(mappedBy = "type", cascade = [CascadeType.ALL])
    val changelog: List<Changelog>
)
