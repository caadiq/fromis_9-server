package com.beemer.fromis9server.service;

import com.beemer.fromis9server.dto.PlatformIconDto;
import com.beemer.fromis9server.dto.ScheduleDto;
import com.beemer.fromis9server.model.Schedule;
import com.beemer.fromis9server.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleDto> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ScheduleDto convertToDto(Schedule schedule) {
        ScheduleDto dto = new ScheduleDto();
        dto.setId(schedule.getId());
        dto.setDate(schedule.getDate());
        dto.setTitle(schedule.getTitle());
        dto.setDescription(schedule.getDescription());
        if (schedule.getPlatformIcon() != null) {
            PlatformIconDto platformIconDto = new PlatformIconDto();
            platformIconDto.setPlatform(schedule.getPlatformIcon().getPlatform());
            platformIconDto.setImageUrl(schedule.getPlatformIcon().getImageUrl());
            dto.setPlatformIcon(platformIconDto);
        }
        return dto;
    }
}