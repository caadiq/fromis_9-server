package com.beemer.fromis9server.album.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumArtDTO {
    private String albumName;
    private String imageUrl;
}
