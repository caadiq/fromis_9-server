package com.beemer.fromis9server.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleDto {
    private Long id;
    private LocalDateTime date;
    private String title;
    private String description;
    private PlatformIconDto platformIcon;
}
