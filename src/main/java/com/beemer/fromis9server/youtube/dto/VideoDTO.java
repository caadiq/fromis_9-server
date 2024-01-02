package com.beemer.fromis9server.youtube.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTO {
    private String videoId;
    private LocalDateTime publishedAt;
    private String title;
    private String description;
    private String thumbnailUrl;
}