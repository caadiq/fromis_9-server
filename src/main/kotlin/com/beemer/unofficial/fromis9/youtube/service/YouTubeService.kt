package com.beemer.unofficial.fromis9.youtube.service

import com.beemer.unofficial.fromis9.common.dto.MessageDto
import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import com.beemer.unofficial.fromis9.schedule.entity.Schedules
import com.beemer.unofficial.fromis9.schedule.repository.PlatformRepository
import com.beemer.unofficial.fromis9.schedule.repository.ScheduleRepository
import com.beemer.unofficial.fromis9.youtube.dto.*
import com.beemer.unofficial.fromis9.youtube.entity.YouTubeVideoDetails
import com.beemer.unofficial.fromis9.youtube.entity.YouTubeVideos
import com.beemer.unofficial.fromis9.youtube.repository.YouTubeVideoRepository
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.Instant
import java.time.ZoneId

@Service
class YouTubeService(
    private val youTubeVideoRepository: YouTubeVideoRepository,
    private val scheduleRepository: ScheduleRepository,
    private val platformRepository: PlatformRepository,
    private val webClient: WebClient
) {
    @Value("\${youtube.api.key}")
    private lateinit var apiKey: String

    @Value("\${fast.api.url}")
    private lateinit var fastApiUrl: String

    private val logger = LoggerFactory.getLogger(YouTubeService::class.java)

    private val gsonFactory = GsonFactory.getDefaultInstance()
    val youtube = YouTube.Builder(NetHttpTransport(), gsonFactory) { }
        .setApplicationName("YoutubeVideoFetcher")
        .build()

    fun getVideoList(playlist: String?, page: Int, limit: Int, query: String?) : ResponseEntity<YouTubeListDto> {
        val limitAdjusted = 1.coerceAtLeast(50.coerceAtMost(limit))
        val pageable = PageRequest.of(page, limitAdjusted, Sort.by("publishedAt").descending())

        val youTubeVideos = youTubeVideoRepository.findByTitleAndPlaylist(pageable, query, playlist)

        if (youTubeVideos.content.isEmpty() && youTubeVideos.totalElements > 0) {
            throw CustomException(ErrorCode.VIDEO_NOT_FOUND)
        }

        val prevPage = if (youTubeVideos.hasPrevious()) youTubeVideos.number - 1 else null
        val currentPage = youTubeVideos.number
        val nextPage = if (youTubeVideos.hasNext()) youTubeVideos.number + 1 else null

        val pages = YouTubePageDto(prevPage, currentPage, nextPage)

        val videos = youTubeVideos.content.map {
            YouTubeDto(
                it.videoId,
                it.title,
                it.thumbnail,
                it.publishedAt,
                it.publishedAt.isAfter(Instant.now().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime().minusDays(7)),
                it.description,
                it.details?.length,
                it.details?.views
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(YouTubeListDto(pages, videos))
    }

    @Transactional
    fun getVideoDetails() {
        val url = "$fastApiUrl/youtube"

        val videoList: List<VideoDetailsRequestDto> = youTubeVideoRepository.findAll().map {
            VideoDetailsRequestDto(it.videoId)
        }

        logger.info("영상 상세 정보 요청 : $videoList")

        webClient.post()
            .uri(url)
            .bodyValue(videoList)
            .retrieve()
            .bodyToFlux(VideoDetailsResponseDto::class.java)
            .subscribe(this::saveVideoDetails)
    }

    private fun saveVideoDetails(dto: VideoDetailsResponseDto) {
        val video = youTubeVideoRepository.findById(dto.videoId)
            .orElseThrow { CustomException(ErrorCode.VIDEO_NOT_FOUND) }

        val details = YouTubeVideoDetails(
            dto.videoId,
            dto.length,
            dto.views,
            video
        )

        video.details = details

        logger.info("영상 상세 정보 : ${dto.videoId}: 영상 길이(${dto.length}), 조회수(${dto.views})")

        youTubeVideoRepository.save(video)
    }

    @Transactional
    fun fetchYoutubeVideo(videoId: String) : ResponseEntity<MessageDto> {
        val url = "https://www.youtube.com/watch?v=$videoId"

        if (scheduleRepository.existsByUrl(url)) {
            throw CustomException(ErrorCode.VIDEO_ALREADY_EXISTS)
        }

        val request = youtube.videos().list(listOf("snippet", "contentDetails")).apply {
            key = apiKey
            this.id = listOf(videoId)
        }

        request.execute().items[0].let {
            val title = it.snippet.title
            val publishedAt = Instant.ofEpochMilli(it.snippet.publishedAt.value)
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime()

            val youtube = youTubeVideoRepository.findById(videoId)
                .orElse(null)
            if (youtube == null) {
                val newVideo = YouTubeVideos(
                    videoId,
                    title,
                    it.snippet.thumbnails.default.url,
                    publishedAt,
                    it.snippet.description
                )
                youTubeVideoRepository.save(newVideo)
            }

            val schedule = scheduleRepository.findBySchedule(title)
                .orElse(null)
            val platform = platformRepository.findById("youtube")
                .orElseThrow { CustomException(ErrorCode.PLATFORM_NOT_FOUND) }
            if (schedule == null) {
                val newSchedule = Schedules(
                    platform,
                    publishedAt,
                    title,
                    it.snippet.channelTitle,
                    "https://www.youtube.com/watch?v=$videoId",
                    false
                )
                scheduleRepository.save(newSchedule)
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageDto("영상이 추가되었습니다."))
    }
}