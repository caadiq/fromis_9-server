package com.beemer.fromis9server.album.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PhotoListDTO {
    private List<PhotoDTO> photo;
}
