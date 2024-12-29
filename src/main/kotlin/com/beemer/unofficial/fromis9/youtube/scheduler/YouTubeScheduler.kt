package com.beemer.unofficial.fromis9.youtube.scheduler

import com.beemer.unofficial.fromis9.youtube.service.YouTubeService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class YouTubeScheduler(private val youtubeService: YouTubeService) {

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    fun getVideoDetails() {
        youtubeService.getVideoDetails()
    }
}