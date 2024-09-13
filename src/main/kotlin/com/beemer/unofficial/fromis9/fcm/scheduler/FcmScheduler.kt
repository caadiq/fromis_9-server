package com.beemer.unofficial.fromis9.fcm.scheduler

import com.beemer.unofficial.fromis9.fcm.service.FcmService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

enum class Member {
    SAEROM,
    HAYOUNG,
    JIWON,
    JISUN,
    SEOYEON,
    CHAEYOUNG,
    NAKYUNG,
    JIHEON
}

@Component
class FcmScheduler(private val fcmService: FcmService) {

    @Scheduled(cron = "0 6 1 * * *")
    @Scheduled(cron = "0 6 13 * * *")
    fun sendMemberTimeSaerom() {
        fcmService.sendMemberTimeNoti(Member.SAEROM)
    }

    @Scheduled(cron = "0 28 9 * * *")
    @Scheduled(cron = "0 28 21 * * *")
    fun sendMemberTimeHayoung() {
        fcmService.sendMemberTimeNoti(Member.HAYOUNG)
    }

    @Scheduled(cron = "0 19 3 * * *")
    @Scheduled(cron = "0 19 15 * * *")
    fun sendMemberTimeJiwon() {
        fcmService.sendMemberTimeNoti(Member.JIWON)
    }

    @Scheduled(cron = "0 22 11 * * *")
    @Scheduled(cron = "0 22 23 * * *")
    fun sendMemberTimeJisun() {
        fcmService.sendMemberTimeNoti(Member.JISUN)
    }

    @Scheduled(cron = "0 21 1 * * *")
    @Scheduled(cron = "0 21 13 * * *")
    fun sendMemberTimeSeoyeon() {
        fcmService.sendMemberTimeNoti(Member.SEOYEON)
    }

    @Scheduled(cron = "0 13 5 * * *")
    @Scheduled(cron = "0 13 17 * * *")
    fun sendMemberTimeChaeyoung() {
        fcmService.sendMemberTimeNoti(Member.CHAEYOUNG)
    }

    @Scheduled(cron = "0 0 6 * * *")
    @Scheduled(cron = "0 0 18 * * *")
    fun sendMemberTimeNakyung() {
        fcmService.sendMemberTimeNoti(Member.NAKYUNG)
    }

    @Scheduled(cron = "0 16 4 * * *")
    @Scheduled(cron = "0 16 16 * * *")
    fun sendMemberTimeJiheon() {
        fcmService.sendMemberTimeNoti(Member.JIHEON)
    }
}