package com.beemer.unofficial.fromis9.album.controller

import com.beemer.unofficial.fromis9.album.dto.AlbumDetailsDto
import com.beemer.unofficial.fromis9.album.dto.AlbumListDto
import com.beemer.unofficial.fromis9.album.dto.SongDetailsDto
import com.beemer.unofficial.fromis9.album.dto.SongListDto
import com.beemer.unofficial.fromis9.album.service.AlbumService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/album")
class AlbumController(private val albumService: AlbumService) {

    @GetMapping("/list")
    fun getAlbumList() : ResponseEntity<List<AlbumListDto>> {
        return albumService.getAlbumList()
    }

    @GetMapping("/details")
    fun getAlbumDetails(@RequestParam album: String) : ResponseEntity<AlbumDetailsDto> {
        return albumService.getAlbumDetails(album)
    }

    @GetMapping("/song")
    fun getSongDetails(@RequestParam name: String) : ResponseEntity<SongDetailsDto> {
        return albumService.getSongDetails(name)
    }

    @GetMapping("/songs")
    fun getSongList() : ResponseEntity<List<SongListDto>> {
        return albumService.getSongList()
    }
}