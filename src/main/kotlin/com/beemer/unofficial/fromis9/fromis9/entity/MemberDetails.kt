package com.beemer.unofficial.fromis9.fromis9.entity

import jakarta.persistence.*

@Entity
@Table(name = "MemberDetails")
data class MemberDetails(
    @Id
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "position", nullable = false)
    val position: String,

    @Column(name = "instagram", nullable = false)
    val instagram: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "name")
    @MapsId
    val member: Members
)
