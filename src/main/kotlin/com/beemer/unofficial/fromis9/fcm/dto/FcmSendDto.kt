package com.beemer.unofficial.fromis9.fcm.dto

data class FcmSendDto(
    val token: String,
    val title: String,
    val body: String
)
