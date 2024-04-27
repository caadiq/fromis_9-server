package com.beemer.unofficial.fromis9.album.service

import com.beemer.unofficial.fromis9.album.dto.AlbumListDto
import com.beemer.unofficial.fromis9.album.repository.AlbumRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class AlbumService(private val albumRepository: AlbumRepository) {
    private val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    // 앨범 목록
    fun getAlbumList() : ResponseEntity<List<AlbumListDto>> {
        val albumList = albumRepository.findAllByOrderByReleaseDesc().map {
            AlbumListDto(
                albumName = it.albumName,
                type = it.type,
                cover = it.cover,
                release = it.release.format(formatter),
                colorMain = it.colorMain,
                colorPrimary = it.colorPrimary,
                colorSecondary = it.colorSecondary
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(albumList)
    }
}