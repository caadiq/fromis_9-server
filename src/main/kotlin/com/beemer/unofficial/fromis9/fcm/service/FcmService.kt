package com.beemer.unofficial.fromis9.fcm.service

import com.beemer.unofficial.fromis9.common.dto.MessageDto
import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import com.beemer.unofficial.fromis9.fcm.dto.FcmSendDto
import com.beemer.unofficial.fromis9.fcm.dto.FcmTokenDto
import com.beemer.unofficial.fromis9.fcm.entity.FcmTokens
import com.beemer.unofficial.fromis9.fcm.repository.FcmRepository
import com.beemer.unofficial.fromis9.fcm.utils.FcmUtils
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FcmService(
    private val fcmRepository: FcmRepository,
    private val fcmUtils: FcmUtils
) {
    @Transactional
    fun saveFcmToken(dto: FcmTokenDto) : ResponseEntity<MessageDto> {
        fcmRepository.save(FcmTokens(dto.ssaid, dto.token))
        return ResponseEntity.status(HttpStatus.OK).body(MessageDto("FcmToken 저장 완료"))
    }

    fun sendMessage(dto: FcmSendDto) : ResponseEntity<MessageDto> {
        try {
            fcmUtils.sendMessateTo(dto.token, dto.title, dto.body)
            return ResponseEntity.status(HttpStatus.OK).body(MessageDto("메시지 전송 완료"))
        } catch (_: Exception) {
            throw CustomException(ErrorCode.FCM_SEND_FAIL)
        }
    }
}