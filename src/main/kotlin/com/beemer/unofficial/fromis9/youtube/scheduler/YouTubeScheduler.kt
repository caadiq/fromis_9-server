package com.beemer.unofficial.fromis9.youtube.scheduler

import com.beemer.unofficial.fromis9.youtube.service.YouTubeService
import jakarta.annotation.PostConstruct
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class YouTubeScheduler(private val youtubeService: YouTubeService) {

    @PostConstruct
    fun init() {
        youtubeService.getVideoDetails()
    }

    @Scheduled(cron = "0 0,20,40 * * * *")
    fun getVideoDetails() {
        youtubeService.getVideoDetails()
    }
}