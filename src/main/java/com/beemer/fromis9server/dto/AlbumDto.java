package com.beemer.fromis9server.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AlbumDto {
    private Long id;
    private LocalDate date;
    private String tilte;
    private String classify;
    private String colorPrimary;
    private String colorSecondary;
    private AlbumArtDto albumArt;
}
