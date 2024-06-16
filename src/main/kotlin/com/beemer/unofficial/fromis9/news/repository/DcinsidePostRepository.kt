package com.beemer.unofficial.fromis9.news.repository

import com.beemer.unofficial.fromis9.news.entity.DcinsidePosts
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface DcinsidePostRepository : JpaRepository<DcinsidePosts, Int> {
    fun findTop10ByOrderByDateDesc(): List<DcinsidePosts>

    fun findByDateBetween(start: LocalDateTime, end: LocalDateTime): List<DcinsidePosts>
}