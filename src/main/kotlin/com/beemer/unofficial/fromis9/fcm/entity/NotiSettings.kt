package com.beemer.unofficial.fromis9.fcm.entity

import jakarta.persistence.*

@Entity
@Table(name = "NotiSettings")
data class NotiSettings(
    @Id
    @Column(name = "ssaid", nullable = false)
    val ssaid: String? = null,

    @Column(name = "member_time", nullable = false)
    var memberTime: Boolean,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ssaid")
    @MapsId
    val fcmToken: FcmTokens
)