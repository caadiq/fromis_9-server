package com.beemer.unofficial.fromis9.news.repository

import com.beemer.unofficial.fromis9.news.entity.WeverseNotice
import org.springframework.data.jpa.repository.JpaRepository

interface WeverseNoticeRepository : JpaRepository<WeverseNotice, Int> {
    fun findTop10ByOrderByDateDesc(): List<WeverseNotice>
}