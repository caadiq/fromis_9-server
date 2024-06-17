package com.beemer.unofficial.fromis9.changelog.repository

import com.beemer.unofficial.fromis9.changelog.entity.AppVersion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppVersionRepository : JpaRepository<AppVersion, String> {
    fun findTopByOrderByDateDesc(): AppVersion
}