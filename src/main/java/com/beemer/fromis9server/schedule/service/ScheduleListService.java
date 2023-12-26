package com.beemer.fromis9server.schedule.service;

import com.beemer.fromis9server.schedule.dto.ScheduleIconDTO;
import com.beemer.fromis9server.schedule.dto.ScheduleListDTO;
import com.beemer.fromis9server.schedule.model.ScheduleList;
import com.beemer.fromis9server.schedule.repository.ScheduleListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleListService {
    private final ScheduleListRepository scheduleListRepository;

    @Autowired
    public ScheduleListService(ScheduleListRepository scheduleListRepository) {
        this.scheduleListRepository = scheduleListRepository;
    }

    public List<ScheduleListDTO> getScheduleList(int year, int month) {
        List<ScheduleList> scheduleList = scheduleListRepository.getScheduleByYearAndMonth(year, month);
        return scheduleList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ScheduleListDTO convertToDTO(ScheduleList scheduleList) {
        ScheduleListDTO scheduleListDTO = new ScheduleListDTO();
        scheduleListDTO.setId(scheduleList.getId());
        scheduleListDTO.setDateTime(scheduleList.getDateTime());
        scheduleListDTO.setSchedule(scheduleList.getSchedule());
        scheduleListDTO.setDescription(scheduleList.getDescription());

        if (scheduleList.getScheduleIcon() != null) {
            ScheduleIconDTO scheduleIconDTO = new ScheduleIconDTO();
            scheduleIconDTO.setPlatform(scheduleList.getScheduleIcon().getPlatform());
            scheduleIconDTO.setImageUrl(scheduleList.getScheduleIcon().getImageUrl());
            scheduleListDTO.setScheduleIconDTO(scheduleIconDTO);
        }

        return scheduleListDTO;
    }
}
