package com.beemer.unofficial.fromis9.youtube.scheduler

import com.beemer.unofficial.fromis9.youtube.service.YouTubeChannel
import com.beemer.unofficial.fromis9.youtube.service.YouTubeService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class YouTubeScheduler(private val youtubeService: YouTubeService) {

    @Scheduled(cron = "0 1,6,11,16,21,26,31,36,41,46,51,56 * * * *")
    fun fetchFromis9Playlist() {
        youtubeService.fetchYouTubePlaylist(YouTubeChannel.FROMIS_9.playlistId)
    }

    @Scheduled(cron = "0 1 18,21 * * *")
    fun fetchHybeLabelsPlaylist() {
        youtubeService.fetchYouTubePlaylist(YouTubeChannel.HYBE_LABELS.playlistId)
    }

    @Scheduled(cron = "0 2,32 * * * *")
    fun getVideoDetails() {
        youtubeService.getVideoDetails()
    }
}