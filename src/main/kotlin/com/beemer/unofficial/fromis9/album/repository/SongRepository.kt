package com.beemer.unofficial.fromis9.album.repository

import com.beemer.unofficial.fromis9.album.entity.Albums
import com.beemer.unofficial.fromis9.album.entity.Songs
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<Songs, String> {
    fun findByAlbumNameOrderByTrack(albums: Albums): List<Songs>

    @Query("SELECT s FROM Songs s JOIN s.albumName a ORDER BY a.release DESC, s.track ASC")
    fun findAllOrderByAlbumReleaseDescAndTrackAsc(): List<Songs>
}