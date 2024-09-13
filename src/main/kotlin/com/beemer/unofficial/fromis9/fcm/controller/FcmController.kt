package com.beemer.unofficial.fromis9.fcm.controller

import com.beemer.unofficial.fromis9.common.dto.MessageDto
import com.beemer.unofficial.fromis9.fcm.dto.FcmNotiDto
import com.beemer.unofficial.fromis9.fcm.dto.FcmSendDto
import com.beemer.unofficial.fromis9.fcm.dto.FcmTokenDto
import com.beemer.unofficial.fromis9.fcm.service.FcmService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/fromis9/")
class FcmController(
    private val fcmService: FcmService
) {
    @PostMapping("/fcm/token")
    fun saveFcmToken(
        @RequestBody dto: FcmTokenDto
    ) : ResponseEntity<MessageDto> {
        return fcmService.saveFcmToken(dto)
    }

    @PostMapping("/fcm/send")
    fun sendMessage(
        @RequestBody dto: FcmSendDto
    ) : ResponseEntity<MessageDto> {
        return fcmService.sendMessage(dto)
    }

    @PostMapping("/fcm/noti/membertime")
    fun setMemberTime(
        @RequestBody dto: FcmNotiDto
    ) : ResponseEntity<MessageDto> {
        return fcmService.setMemberTime(dto)
    }
}