package com.beemer.unofficial.fromis9.fcm.service

import com.beemer.unofficial.fromis9.common.dto.MessageDto
import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import com.beemer.unofficial.fromis9.fcm.dto.FcmNotiDto
import com.beemer.unofficial.fromis9.fcm.dto.FcmSendDto
import com.beemer.unofficial.fromis9.fcm.dto.FcmTokenDto
import com.beemer.unofficial.fromis9.fcm.entity.FcmTokens
import com.beemer.unofficial.fromis9.fcm.entity.NotiSettings
import com.beemer.unofficial.fromis9.fcm.repository.FcmRepository
import com.beemer.unofficial.fromis9.fcm.repository.NotiSettingsRepository
import com.beemer.unofficial.fromis9.fcm.scheduler.Member
import com.beemer.unofficial.fromis9.fcm.utils.FcmUtils
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FcmService(
    private val fcmRepository: FcmRepository,
    private val notiSettingsRepository: NotiSettingsRepository,
    private val fcmUtils: FcmUtils
) {
    @Transactional
    fun saveFcmToken(dto: FcmTokenDto) : ResponseEntity<MessageDto> {
        var fcmToken = FcmTokens(dto.ssaid, dto.token)
        fcmRepository.save(fcmToken)

        fcmToken = fcmRepository.findById(dto.ssaid)
            .orElseThrow { CustomException(ErrorCode.SSAID_NOT_FOUND) }

        val notiSettings = NotiSettings(memberTime = false, fcmToken = fcmToken)
        notiSettingsRepository.save(notiSettings)

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

    fun setMemberTime(dto: FcmNotiDto) : ResponseEntity<MessageDto> {
        val notiSettings = notiSettingsRepository.findById(dto.ssaid)
            .orElseThrow { CustomException(ErrorCode.SSAID_NOT_FOUND) }
        notiSettings.memberTime = dto.isChecked
        notiSettingsRepository.save(notiSettings)

        return ResponseEntity.status(HttpStatus.OK).body(MessageDto("알림 설정 변경 완료"))
    }

    fun sendMemberTimeNoti(member: Member) {
        val notiSettings = notiSettingsRepository.findAllByMemberTime(true)
        val memberName = when(member) {
            Member.SAEROM -> "새롬"
            Member.HAYOUNG -> "하영"
            Member.JIWON -> "지원"
            Member.JISUN -> "지선"
            Member.SEOYEON -> "서연"
            Member.NAKYUNG -> "나경"
            Member.CHAEYOUNG -> "채영"
            Member.JIHEON -> "지헌"
        }

        notiSettings.forEach {
            fcmUtils.sendMessateTo(it.fcmToken.token, "멤버시 알림", "${memberName}시 1분 전입니다.")
        }
    }
}