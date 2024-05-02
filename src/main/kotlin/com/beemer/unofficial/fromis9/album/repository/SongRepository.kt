package com.beemer.unofficial.fromis9.album.repository

import com.beemer.unofficial.fromis9.album.entity.Albums
import com.beemer.unofficial.fromis9.album.entity.Songs
import org.springframework.data.jpa.repository.JpaRepository

interface SongRepository : JpaRepository<Songs, String> {
    fun findByAlbumNameOrderByTrack(albums: Albums): List<Songs>
}