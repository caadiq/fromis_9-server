package com.beemer.fromis9server.appinfo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppInfoDTO {
    private String version;
    private LocalDate release;
    private String changelog;
}
