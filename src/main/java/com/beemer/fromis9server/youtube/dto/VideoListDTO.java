package com.beemer.fromis9server.youtube.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VideoListDTO {
    private VideoPageDTO page;
    private List<VideoDTO> videos;
}