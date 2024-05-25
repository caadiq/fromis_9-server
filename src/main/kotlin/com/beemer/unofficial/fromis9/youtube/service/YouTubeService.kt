package com.beemer.unofficial.fromis9.youtube.service

import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import com.beemer.unofficial.fromis9.youtube.dto.*
import com.beemer.unofficial.fromis9.youtube.entity.YouTubeVideoDetails
import com.beemer.unofficial.fromis9.youtube.entity.YouTubeVideos
import com.beemer.unofficial.fromis9.youtube.repository.YouTubeVideoRepository
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.PlaylistItemListResponse
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.Instant
import java.time.ZoneId

enum class YouTubeChannel(val playlistId: String) {
    FROMIS_9("UU8qO5racajmy4YgPgNJkVXg"),
    HYBE_LABELS("PL_Cqw69_m_yyAZ1QABPigf6hqpSzMYsBT"),
}

@Service
class YouTubeService(
    private val youTubeVideoRepository: YouTubeVideoRepository,
    private val webClient: WebClient
) {
    @Value("\${youtube.api.key}")
    private lateinit var apiKey: String

    @Value("\${fast.api.url}")
    private lateinit var fastApiUrl: String

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
                it.description,
                it.details?.length,
                it.details?.views
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(YouTubeListDto(pages, videos))
    }

    @Transactional
    fun fetchYouTubePlaylist(playlistId: String) {
        var nextPageToken: String? = null
        val youTubeVideoList = mutableListOf<YouTubeVideos>()

        do {
            val response = getPlayListVideos(playlistId, nextPageToken)

            val youTubeVideosList = response.items.map { item ->
                val videoId = item.contentDetails.videoId
                val title = item.snippet.title
                val thumbnail = item.snippet.thumbnails.run {
                    maxres?.url ?: high?.url ?: medium?.url ?: default.url
                }
                val publishedAt = Instant.ofEpochMilli(item.contentDetails.videoPublishedAt.value)
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                    .toLocalDateTime()
                val description = item.snippet.description

                YouTubeVideos(
                    videoId,
                    title,
                    thumbnail,
                    publishedAt,
                    description
                )
            }

            youTubeVideoList.addAll(youTubeVideosList)

            nextPageToken = response.nextPageToken
        } while (!nextPageToken.isNullOrEmpty())

        youTubeVideoRepository.saveAll(youTubeVideoList)
    }

    private fun getPlayListVideos(playlistId: String, pageToken: String?): PlaylistItemListResponse {
        val gsonFactory = GsonFactory.getDefaultInstance()

        val youtube = YouTube.Builder(
            NetHttpTransport(),
            gsonFactory
        ) { }
            .setApplicationName("Fromis9VideoListFetcher")
            .build()

        val request = youtube.playlistItems().list(listOf("snippet", "contentDetails")).apply {
            key = apiKey
            this.playlistId = playlistId
            maxResults = 50
        }

        if (!pageToken.isNullOrEmpty()) {
            request.pageToken = pageToken
        }

        return request.execute()
    }

    @Transactional
    fun getVideoDetails() {
        val url = "$fastApiUrl/youtube"

        val videoList: List<VideoDetailsRequestDto> = youTubeVideoRepository.findAll().map {
            VideoDetailsRequestDto(it.videoId)
        }

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

        youTubeVideoRepository.save(video)
    }
}