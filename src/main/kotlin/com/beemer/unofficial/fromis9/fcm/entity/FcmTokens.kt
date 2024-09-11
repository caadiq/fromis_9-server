package com.beemer.unofficial.fromis9.fcm.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "FcmTokens")
data class FcmTokens(
    @Id
    @Column(name = "ssaid", nullable = false)
    val ssaid: String,

    @Column(name = "token", nullable = false)
    val token: String
)