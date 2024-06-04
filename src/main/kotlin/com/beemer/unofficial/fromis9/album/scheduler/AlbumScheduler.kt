package com.beemer.unofficial.fromis9.album.scheduler

import com.beemer.unofficial.fromis9.album.service.AlbumService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class AlbumScheduler(private val albumService: AlbumService) {

    @Scheduled(cron = "0 5 * * * *")
    fun fetchWeverseShopAlbums() {
        albumService.getWeverseShopAlbums()
    }
}