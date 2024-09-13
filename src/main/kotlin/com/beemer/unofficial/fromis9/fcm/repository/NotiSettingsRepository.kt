package com.beemer.unofficial.fromis9.fcm.repository

import com.beemer.unofficial.fromis9.fcm.entity.NotiSettings
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface NotiSettingsRepository : JpaRepository<NotiSettings, String> {

    @Query("SELECT n FROM NotiSettings n WHERE n.memberTime = :memberTime")
    fun findAllByMemberTime(memberTime: Boolean): List<NotiSettings>
}