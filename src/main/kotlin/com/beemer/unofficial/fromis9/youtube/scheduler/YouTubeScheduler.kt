package com.beemer.unofficial.fromis9.youtube.scheduler

import com.beemer.unofficial.fromis9.youtube.service.YouTubeChannel
import com.beemer.unofficial.fromis9.youtube.service.YouTubeService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class YouTubeScheduler(private val youtubeService: YouTubeService) {

    @Scheduled(cron = "0 1,11,21,31,41,51 * * * *")
    fun fetchFromis9Playlist() {
        youtubeService.fetchYouTubePlaylist(YouTubeChannel.FROMIS_9.playlistId)
    }

    @Scheduled(cron = "0 1 18 * * *")
    fun fetchHybeLabelsPlaylist() {
        youtubeService.fetchYouTubePlaylist(YouTubeChannel.HYBE_LABELS.playlistId)
    }
}