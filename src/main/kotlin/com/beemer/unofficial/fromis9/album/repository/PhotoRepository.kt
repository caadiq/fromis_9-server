package com.beemer.unofficial.fromis9.album.repository

import com.beemer.unofficial.fromis9.album.entity.Albums
import com.beemer.unofficial.fromis9.album.entity.Photos
import org.springframework.data.jpa.repository.JpaRepository

interface PhotoRepository : JpaRepository<Photos, Int> {
    fun findByAlbumName(albums: Albums): List<Photos>
}