package com.beemer.unofficial.fromis9.fromis9.repository

import com.beemer.unofficial.fromis9.fromis9.entity.Members
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Members, String>