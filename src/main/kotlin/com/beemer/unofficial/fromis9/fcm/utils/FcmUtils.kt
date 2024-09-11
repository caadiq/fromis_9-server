package com.beemer.unofficial.fromis9.fcm.utils

import com.beemer.unofficial.fromis9.fcm.dto.Data
import com.beemer.unofficial.fromis9.fcm.dto.FcmMessageDto
import com.beemer.unofficial.fromis9.fcm.dto.Message
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import java.io.IOException

@Component
class FcmUtils(
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper
) {
    @Value("\${fcm.api.url}")
    private lateinit var fcmApiUrl: String

    fun sendMessateTo(token: String, title: String, body: String) {
        val message = makeMessage(token, title, body)
        val accessToken = getAccessToken()

        if (message != null && accessToken != null) {
            webClient.post()
                .uri(fcmApiUrl)
                .header("Authorization", "Bearer $accessToken")
                .header("Content-Type", "application/json; UTF-8")
                .body(BodyInserters.fromValue(message))
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
        }
    }

    private fun makeMessage(token: String, title: String, body: String) : String? {
        try {
            val fcmMessage = FcmMessageDto(Message(token, Data(title, body)))
            return objectMapper.writeValueAsString(fcmMessage)
        } catch (_: JsonProcessingException) {
            return null
        }
    }

    private fun getAccessToken() : String? {
        val firebaseConfigPath = "firebase/firebase-admin-key.json"

        try {
            val googleCredentials = GoogleCredentials
                .fromStream(FcmUtils::class.java.classLoader.getResourceAsStream(firebaseConfigPath))
                .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))

            googleCredentials.refreshIfExpired()

            return googleCredentials.accessToken.tokenValue
        } catch (_: IOException) {
            return null
        }
    }
}