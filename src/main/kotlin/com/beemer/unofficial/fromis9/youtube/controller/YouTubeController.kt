package com.beemer.unofficial.fromis9.youtube.controller

import com.beemer.unofficial.fromis9.common.dto.MessageDto
import com.beemer.unofficial.fromis9.youtube.dto.YouTubeListDto
import com.beemer.unofficial.fromis9.youtube.service.YouTubeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/video")
class YouTubeController(private val youTubeService: YouTubeService) {

    @GetMapping("/list")
    fun getVideoList(
        @RequestParam playlist: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") limit: Int,
        @RequestParam query: String?
    ) : ResponseEntity<YouTubeListDto> {
        return youTubeService.getVideoList(playlist, page, limit, query)
    }

    @PostMapping("/add")
    fun addVideo(
        @RequestParam videoId: String
    ) : ResponseEntity<MessageDto> {
        return youTubeService.fetchYoutubeVideo(videoId)
    }
}