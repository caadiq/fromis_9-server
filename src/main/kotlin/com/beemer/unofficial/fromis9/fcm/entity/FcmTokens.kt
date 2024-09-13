package com.beemer.unofficial.fromis9.fcm.entity

import jakarta.persistence.*

@Entity
@Table(name = "FcmTokens")
data class FcmTokens(
    @Id
    @Column(name = "ssaid", nullable = false)
    val ssaid: String,

    @Column(name = "token", nullable = false)
    val token: String,

    @OneToOne(mappedBy = "fcmToken", cascade = [CascadeType.ALL])
    var notiSettings: NotiSettings? = null
)