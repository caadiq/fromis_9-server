package com.beemer.unofficial.fromis9.news.repository

import com.beemer.unofficial.fromis9.news.entity.WeverseNotice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface WeverseNoticeRepository : JpaRepository<WeverseNotice, Int> {
    fun findTop10ByOrderByDateDesc(): List<WeverseNotice>

    fun findByDateBetween(start: LocalDateTime, end: LocalDateTime): List<WeverseNotice>
}