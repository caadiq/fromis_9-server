package com.beemer.unofficial.fromis9.album.repository

import com.beemer.unofficial.fromis9.album.entity.Albums
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumRepository : JpaRepository<Albums, String> {
    fun findAllByOrderByReleaseDesc(): List<Albums>
}