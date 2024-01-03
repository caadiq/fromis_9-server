package com.beemer.fromis9server.youtube.repository;

import com.beemer.fromis9server.youtube.model.YouTubeVideoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface YouTubeVideoRepository extends JpaRepository<YouTubeVideoList, String> {
    @Query("SELECT v FROM YouTubeVideoList v " +
            "WHERE (v.title LIKE '%MV%' OR v.title LIKE '%M/V%') " +
            "AND LOWER(v.title) NOT LIKE '%teaser%' " +
            "AND LOWER(v.title) NOT LIKE '%reaction%'")
    Page<YouTubeVideoList> findMV(Pageable pageable);

    @Query("SELECT v FROM YouTubeVideoList v WHERE (LOWER(v.title) LIKE '%channel_9%' OR LOWER(v.title) LIKE '%ch.9%')")
    Page<YouTubeVideoList> findChannel9(Pageable pageable);

    @Query("SELECT v FROM YouTubeVideoList v WHERE LOWER(v.title) LIKE '%fm_1.24%'")
    Page<YouTubeVideoList> findFM124(Pageable pageable);

    @Query("SELECT v FROM YouTubeVideoList v WHERE LOWER(v.title) LIKE '%9_log%'")
    Page<YouTubeVideoList> find9log(Pageable pageable);

    @Query("SELECT v FROM YouTubeVideoList v WHERE LOWER(v.title) LIKE '%fromisoda%'")
    Page<YouTubeVideoList> findFromisoda(Pageable pageable);

    @Query("SELECT v FROM YouTubeVideoList v WHERE replace(LOWER(v.title), ' ', '') LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<YouTubeVideoList> findByTitle(String title, Pageable pageable);
}