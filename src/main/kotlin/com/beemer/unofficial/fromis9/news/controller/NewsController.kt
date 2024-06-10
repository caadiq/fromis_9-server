package com.beemer.unofficial.fromis9.news.controller

import com.beemer.unofficial.fromis9.news.dto.WeverseShopAlbumListDto
import com.beemer.unofficial.fromis9.news.service.NewsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/fromis9/news")
class NewsController(private val newsService: NewsService) {

    @GetMapping("/weverse/shop")
    fun getWeverseShopAlbums() : ResponseEntity<List<WeverseShopAlbumListDto>> {
        return newsService.getWeverseShopAlbums()
    }
}