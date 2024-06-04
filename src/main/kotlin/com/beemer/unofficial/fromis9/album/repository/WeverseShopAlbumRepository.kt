package com.beemer.unofficial.fromis9.album.repository

import com.beemer.unofficial.fromis9.album.entity.WeverseShopAlbums
import org.springframework.data.jpa.repository.JpaRepository

interface WeverseShopAlbumRepository : JpaRepository<WeverseShopAlbums, Int>