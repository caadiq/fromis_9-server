package com.beemer.unofficial.fromis9.news.repository

import com.beemer.unofficial.fromis9.news.entity.WeverseLive
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WeverseLiveRepository : JpaRepository<WeverseLive, String>