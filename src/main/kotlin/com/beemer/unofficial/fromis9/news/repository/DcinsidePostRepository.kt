package com.beemer.unofficial.fromis9.news.repository

import com.beemer.unofficial.fromis9.news.entity.DcinsidePosts
import org.springframework.data.jpa.repository.JpaRepository

interface DcinsidePostRepository : JpaRepository<DcinsidePosts, Int> {
    fun findTop10ByOrderByDateDesc(): List<DcinsidePosts>
}