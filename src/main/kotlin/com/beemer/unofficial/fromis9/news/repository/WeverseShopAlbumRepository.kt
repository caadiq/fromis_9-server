package com.beemer.unofficial.fromis9.news.repository

import com.beemer.unofficial.fromis9.news.entity.WeverseShopAlbums
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WeverseShopAlbumRepository : JpaRepository<WeverseShopAlbums, Int>