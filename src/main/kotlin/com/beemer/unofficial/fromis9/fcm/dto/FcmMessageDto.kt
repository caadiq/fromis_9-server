package com.beemer.unofficial.fromis9.fcm.dto

data class FcmMessageDto(
    val message: Message
)

data class Message(
    val token : String,
    val data: Data
)

data class Data(
    val title: String,
    val body: String
)