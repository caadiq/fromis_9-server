package com.beemer.unofficial.fromis9.schedule.repository

import com.beemer.unofficial.fromis9.schedule.entity.Platforms
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlatformRepository : JpaRepository<Platforms, String>