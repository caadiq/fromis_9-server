package com.beemer.unofficial.fromis9.album.service

import com.beemer.unofficial.fromis9.album.dto.AlbumDetailsDto
import com.beemer.unofficial.fromis9.album.dto.AlbumListDto
import com.beemer.unofficial.fromis9.album.dto.PhotoListDto
import com.beemer.unofficial.fromis9.album.dto.TrackListDto
import com.beemer.unofficial.fromis9.album.repository.AlbumRepository
import com.beemer.unofficial.fromis9.album.repository.PhotoRepository
import com.beemer.unofficial.fromis9.album.repository.SongRepository
import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class AlbumService(
    private val albumRepository: AlbumRepository,
    private val photoRepository: PhotoRepository,
    private val songRepository: SongRepository
) {
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

    // 앨범 상세
    fun getAlbumDetails(albumName: String) : ResponseEntity<AlbumDetailsDto> {
        val album = albumRepository.findById(albumName).orElseThrow {
            CustomException(ErrorCode.ALBUM_NOT_FOUND)
        }

        val photoList = photoRepository.findByAlbumName(album).map {
            PhotoListDto(
                photo = it.photo,
                concept = it.concept ?: ""
            )
        }

        val trackList = songRepository.findByAlbumName(album).map {
            TrackListDto(
                trackNumber = it.track,
                songName = it.songName,
                titleTrack = it.title,
                length = it.length
            )
        }

        val albumDetails = album.run {
            AlbumDetailsDto(
                description = description,
                photoList = photoList,
                trackList = trackList
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(albumDetails)
    }
}