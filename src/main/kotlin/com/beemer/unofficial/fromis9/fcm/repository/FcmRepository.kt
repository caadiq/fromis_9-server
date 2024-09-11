package com.beemer.unofficial.fromis9.fcm.repository

import com.beemer.unofficial.fromis9.fcm.entity.FcmTokens
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FcmRepository : JpaRepository<FcmTokens, String>