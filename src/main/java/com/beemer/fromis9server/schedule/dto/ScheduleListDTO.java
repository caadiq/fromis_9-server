package com.beemer.fromis9server.schedule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleListDTO {
    private Long id;
    private LocalDateTime dateTime;
    private String schedule;
    private String description;
    @JsonProperty("icon")
    private ScheduleIconDTO scheduleIconDTO;
}