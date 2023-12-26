package com.beemer.fromis9server.album.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumListDTO {
    private String albumName;
    private String albumType;
    private LocalDate releaseDate;
    private String colorPrimary;
    private String colorSecondary;
    private AlbumArtDTO albumArt;
    private AlbumDescriptionDTO albumDescription;
}