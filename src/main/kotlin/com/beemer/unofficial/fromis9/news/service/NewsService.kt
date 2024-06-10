package com.beemer.unofficial.fromis9.news.service

import com.beemer.unofficial.fromis9.news.dto.WeverseShopAlbumListDto
import com.beemer.unofficial.fromis9.news.dto.WeverseLiveListDto
import com.beemer.unofficial.fromis9.news.dto.WeverseNoticeListDto
import com.beemer.unofficial.fromis9.news.entity.WeverseLive
import com.beemer.unofficial.fromis9.news.entity.WeverseNotice
import com.beemer.unofficial.fromis9.news.entity.WeverseShopAlbums
import com.beemer.unofficial.fromis9.news.repository.WeverseLiveRepository
import com.beemer.unofficial.fromis9.news.repository.WeverseNoticeRepository
import com.beemer.unofficial.fromis9.news.repository.WeverseShopAlbumRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class NewsService(
    private val weverseLiveRepository: WeverseLiveRepository,
    private val weverseNoticeRepository: WeverseNoticeRepository,
    private val weverseShopAlbumRepository: WeverseShopAlbumRepository,
    private val webClient: WebClient
) {
    @Value("\${fast.api.url}")
    private lateinit var fastApiUrl: String

    @Transactional
    fun fetchWeverseLive() {
        val url = "$fastApiUrl/weverse/live"

        webClient.get()
            .uri(url)
            .retrieve()
            .bodyToFlux(WeverseLiveListDto::class.java)
            .subscribe(this::saveWeverseLive)
    }

    private fun saveWeverseLive(dto: WeverseLiveListDto) {
        val weverseLive = WeverseLive(
            liveId = dto.liveId,
            title = dto.title,
            member = dto.members,
            url = dto.url,
            date = dto.date
        )

        weverseLiveRepository.save(weverseLive)
    }

    @Transactional
    fun fetchWeverseNotice() {
        val url = "$fastApiUrl/weverse/notice"

        webClient.get()
            .uri(url)
            .retrieve()
            .bodyToFlux(WeverseNoticeListDto::class.java)
            .subscribe(this::saveWeverseNotice)
    }

    private fun saveWeverseNotice(dto: WeverseNoticeListDto) {
        val weverseNotice = WeverseNotice(
            noticeId = dto.noticeId,
            title = dto.title,
            url = dto.url,
            date = dto.date
        )

        weverseNoticeRepository.save(weverseNotice)
    }

    @Transactional
    fun fetchWeverseShopAlbums() {
        val url = "$fastApiUrl/weverse/shop"

        webClient.get()
            .uri(url)
            .retrieve()
            .bodyToFlux(WeverseShopAlbumListDto::class.java)
            .subscribe(this::saveWeverseShopAlbums)
    }

    private fun saveWeverseShopAlbums(dto: WeverseShopAlbumListDto) {
        val weverseShopAlbums = WeverseShopAlbums(
            albumId = dto.itemId,
            title = dto.title,
            image = dto.img,
            url = dto.url,
            price = dto.price,
            soldOut = dto.isSoldOut
        )

        weverseShopAlbumRepository.save(weverseShopAlbums)
    }

    fun getWeverseShopAlbums(): ResponseEntity<List<WeverseShopAlbumListDto>> {
        val weverseShopAlbums = weverseShopAlbumRepository.findAll().map {
            WeverseShopAlbumListDto(
                itemId = it.albumId,
                title = it.title,
                img = it.image,
                url = it.url,
                price = it.price,
                isSoldOut = it.soldOut
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(weverseShopAlbums)
    }
}