package com.beemer.unofficial.fromis9.fromis9.scheduler

import com.beemer.unofficial.fromis9.fromis9.service.Fromis9Service
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Fromis9Scheduler(private val fromis9Service: Fromis9Service) {

    @Scheduled(cron = "0 0 0 * * *")
    fun fetchFromis9Profile() {
        fromis9Service.fetchFromis9Profile()
    }
}