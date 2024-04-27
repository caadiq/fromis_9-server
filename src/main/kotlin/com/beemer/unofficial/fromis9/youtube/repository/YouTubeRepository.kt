package com.beemer.unofficial.fromis9.youtube.repository

import com.beemer.unofficial.fromis9.youtube.entity.YouTubeVideos
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface YouTubeRepository : JpaRepository<YouTubeVideos, String> {
    @Query(
        "SELECT v FROM YouTubeVideos v WHERE " +
        "CASE " +
        "WHEN :playlist = 'mv' THEN (" +
                "(v.title LIKE '%MV%' OR v.title LIKE '%M/V%') " +
                "AND LOWER(v.title) NOT LIKE '%teaser%' " +
                "AND LOWER(v.title) NOT LIKE '%reaction%' " +
                "AND LOWER(v.title) NOT LIKE '%making%' " +
                "AND LOWER(v.title) NOT LIKE '%behind%' " +
                "AND LOWER(v.title) NOT LIKE '%비하인드%' " +
                "AND LOWER(v.title) NOT LIKE '%리액션%' " +
                "AND LOWER(v.title) NOT LIKE '%촬영%' " +
                "AND v.publishedAt IN (SELECT MIN(v2.publishedAt) FROM YouTubeVideos v2 WHERE v2.title = v.title GROUP BY v2.title) " +
        ") " +
        "WHEN :playlist = 'channel9' THEN (" +
                "(LOWER(v.title) LIKE '%channel_9%' OR LOWER(v.title) LIKE '%ch.9%')" +
        ") " +
        "WHEN :playlist = 'fm124' THEN (LOWER(v.title) LIKE '%fm_1.24%') " +
        "WHEN :playlist = 'vlog' THEN (" +
                "LOWER(v.title) LIKE '%vlog%'" +
                "OR LOWER(v.title) LIKE '%9_log%' " +
                "OR LOWER(v.title) LIKE '%honey_log%' " +
                "OR LOWER(v.title) LIKE '%rommantic day%'" +
        ") " +
        "WHEN :playlist = 'fromisoda' THEN (LOWER(v.title) LIKE '%fromisoda%') " +
        "ELSE TRUE " +
        "END " +
        "AND (:title IS NULL OR replace(LOWER(v.title), ' ', '') LIKE LOWER(CONCAT('%', :title, '%')))"
    )
    fun findByTitleAndPlaylist(pageable: Pageable, title: String?, playlist: String?) : Page<YouTubeVideos>
}