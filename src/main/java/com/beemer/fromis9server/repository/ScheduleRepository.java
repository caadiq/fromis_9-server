package com.beemer.fromis9server.repository;

import com.beemer.fromis9server.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}