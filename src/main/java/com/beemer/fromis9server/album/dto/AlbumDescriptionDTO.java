package com.beemer.fromis9server.album.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumDescriptionDTO {
    private String albumName;
    private String description;
}
