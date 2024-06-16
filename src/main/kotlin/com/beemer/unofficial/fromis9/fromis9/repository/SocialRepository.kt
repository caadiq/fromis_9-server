package com.beemer.unofficial.fromis9.fromis9.repository

import com.beemer.unofficial.fromis9.fromis9.entity.Socials
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SocialRepository : JpaRepository<Socials, String>