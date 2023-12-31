package com.beemer.fromis9server.album.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhotoDTO {
    private int id;
    private String albumName;
    private String concept;
    private String imageUrl;
}