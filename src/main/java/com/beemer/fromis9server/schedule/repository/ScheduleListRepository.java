package com.beemer.fromis9server.schedule.repository;

import com.beemer.fromis9server.schedule.model.ScheduleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleListRepository extends JpaRepository<ScheduleList, String> {
    @Query("SELECT s FROM ScheduleList s WHERE YEAR(s.dateTime) = :year AND MONTH(s.dateTime) = :month ORDER BY s.dateTime ASC")
    List<ScheduleList> getScheduleByYearAndMonth(@Param("year") int year, @Param("month") int month);
}