package com.beemer.unofficial.fromis9.news.repository

import com.beemer.unofficial.fromis9.news.entity.Portals
import org.springframework.data.jpa.repository.JpaRepository

interface PortalRepository : JpaRepository<Portals, String>