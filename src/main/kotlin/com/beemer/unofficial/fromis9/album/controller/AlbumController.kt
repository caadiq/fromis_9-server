package com.beemer.unofficial.fromis9.album.controller

import com.beemer.unofficial.fromis9.album.dto.AlbumListDto
import com.beemer.unofficial.fromis9.album.service.AlbumService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/fromis9/album")
class AlbumController(private val albumService: AlbumService) {

    @GetMapping("/list")
    fun getAlbumList() : ResponseEntity<List<AlbumListDto>> {
        return albumService.getAlbumList()
    }
}