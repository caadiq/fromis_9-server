package com.beemer.unofficial.fromis9.album.service

import com.beemer.unofficial.fromis9.album.dto.*
import com.beemer.unofficial.fromis9.album.entity.WeverseShopAlbums
import com.beemer.unofficial.fromis9.album.repository.AlbumRepository
import com.beemer.unofficial.fromis9.album.repository.PhotoRepository
import com.beemer.unofficial.fromis9.album.repository.SongRepository
import com.beemer.unofficial.fromis9.album.repository.WeverseShopAlbumRepository
import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.format.DateTimeFormatter

@Service
class AlbumService(
    private val albumRepository: AlbumRepository,
    private val photoRepository: PhotoRepository,
    private val songRepository: SongRepository,
    private val weverseShopAlbumRepository: WeverseShopAlbumRepository,
    private val webClient: WebClient
) {
    @Value("\${fast.api.url}")
    private lateinit var fastApiUrl: String

    private val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    // 앨범 목록
    fun getAlbumList(): ResponseEntity<List<AlbumListDto>> {
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
    fun getAlbumDetails(albumName: String): ResponseEntity<AlbumDetailsDto> {
        val album = albumRepository.findById(albumName).orElseThrow {
            CustomException(ErrorCode.ALBUM_NOT_FOUND)
        }

        val photoList = photoRepository.findByAlbumName(album).map {
            PhotoListDto(
                photo = it.photo,
                concept = it.concept ?: ""
            )
        }

        val trackList = songRepository.findByAlbumNameOrderByTrack(album).map {
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

    // 노래 상세
    fun getSongDetails(songName: String): ResponseEntity<SongDetailsDto> {
        val song = songRepository.findById(songName).orElseThrow {
            CustomException(ErrorCode.SONG_NOT_FOUND)
        }

        val songDetails = song.run {
            SongDetailsDto(
                lyricist = lyricist,
                composer = composer,
                arranger = arranger,
                lyrics = lyrics,
                videoId = videoId
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(songDetails)
    }

    // 노래 목록
    fun getSongList(): ResponseEntity<List<SongListDto>> {
        val songList = songRepository.findAllOrderByAlbumReleaseDescAndTrackAsc().map {
            SongListDto(
                songName = it.songName,
                albumName = it.albumName.albumName,
                albumCover = it.albumName.cover,
                colorMain = it.albumName.colorMain,
                colorPrimary = it.albumName.colorPrimary,
                colorSecondary = it.albumName.colorSecondary,
                titleTrack = it.title
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(songList)
    }

    @Transactional
    fun fetchWeverseShopAlbums() {
        val url = "$fastApiUrl/weverseshop"

        weverseShopAlbumRepository.deleteAll()

        webClient.get()
            .uri(url)
            .retrieve()
            .bodyToFlux(WeverseShopAlbumListDto::class.java)
            .subscribe(this::saveWeverseShopAlbums)
    }

    private fun saveWeverseShopAlbums(dto: WeverseShopAlbumListDto) {
        val weverseShopAlbums = WeverseShopAlbums(
            albumId = dto.index,
            title = dto.title,
            image = dto.imgSrc,
            url = dto.url,
            price = dto.price,
            soldOut = dto.isSoldOut
        )

        weverseShopAlbumRepository.save(weverseShopAlbums)
    }

    fun getWeverseShopAlbums(): ResponseEntity<List<WeverseShopAlbumListDto>> {
        val weverseShopAlbums = weverseShopAlbumRepository.findAll().map {
            WeverseShopAlbumListDto(
                index = it.albumId,
                title = it.title,
                imgSrc = it.image,
                url = it.url,
                price = it.price,
                isSoldOut = it.soldOut
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(weverseShopAlbums)
    }
}