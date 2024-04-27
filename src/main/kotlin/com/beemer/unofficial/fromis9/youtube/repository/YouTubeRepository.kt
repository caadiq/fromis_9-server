package com.beemer.unofficial.fromis9.youtube.repository

import com.beemer.unofficial.fromis9.youtube.entity.YouTubeVideos
import org.springframework.data.jpa.repository.JpaRepository

interface YouTubeRepository : JpaRepository<YouTubeVideos, String>