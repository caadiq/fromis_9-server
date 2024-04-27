package com.beemer.unofficial.fromis9.youtube.service

import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import com.beemer.unofficial.fromis9.youtube.dto.YouTubeDto
import com.beemer.unofficial.fromis9.youtube.dto.YouTubeListDto
import com.beemer.unofficial.fromis9.youtube.dto.YouTubePageDto
import com.beemer.unofficial.fromis9.youtube.entity.YouTubeVideos
import com.beemer.unofficial.fromis9.youtube.repository.YouTubeRepository
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.PlaylistItemListResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class YouTubeChannel(val playlistId: String) {
    FROMIS_9("UU8qO5racajmy4YgPgNJkVXg"),
    HYBE_LABELS("PL_Cqw69_m_yyAZ1QABPigf6hqpSzMYsBT"),
}

@Service
class YouTubeService(private val youTubeRepository: YouTubeRepository) {
    @Value("\${youtube.api.key}")
    private lateinit var apiKey: String

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    fun getVideoList(playlist: String?, page: Int, limit: Int, query: String?) : ResponseEntity<YouTubeListDto> {
        val limitAdjusted = 1.coerceAtLeast(50.coerceAtMost(limit))
        val pageable = PageRequest.of(page, limitAdjusted, Sort.by("publishedAt").descending())

        val youTubeVideos = youTubeRepository.findByTitleAndPlaylist(pageable, query, playlist)

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
                it.publishedAt.format(dateTimeFormatter)
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(YouTubeListDto(pages, videos))
    }

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

                YouTubeVideos(
                    videoId,
                    title,
                    thumbnail,
                    publishedAt
                )
            }

            youTubeVideoList.addAll(youTubeVideosList)

            nextPageToken = response.nextPageToken
        } while (!nextPageToken.isNullOrEmpty())

        youTubeRepository.saveAll(youTubeVideoList)
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
}