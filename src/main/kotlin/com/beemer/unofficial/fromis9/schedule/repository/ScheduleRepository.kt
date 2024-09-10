package com.beemer.unofficial.fromis9.schedule.repository

import com.beemer.unofficial.fromis9.schedule.entity.Schedules
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface ScheduleRepository : JpaRepository<Schedules, Int> {
    @Query("SELECT s FROM Schedules s WHERE YEAR(s.date) = :year AND MONTH(s.date) = :month ORDER BY s.date ASC")
    fun findByYearAndMonth(year: Int, month: Int): List<Schedules>

    @Query("SELECT s FROM Schedules s WHERE YEAR(s.date) = :year ORDER BY s.date ASC")
    fun findByYear(year: Int): List<Schedules>

    fun findBySchedule(schedule: String): Optional<Schedules>

    @Query("SELECT s FROM Schedules s WHERE s.schedule LIKE %:query% OR s.description LIKE %:query%")
    fun findByScheduleAndDescription(pageable: Pageable, query: String): Page<Schedules>

    fun existsByDateAndSchedule(date: LocalDateTime, schedule: String): Boolean
}