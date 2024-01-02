package com.beemer.fromis9server.youtube.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "YouTubeVideoList")
public class YouTubeVideoList {
    @Id
    private String videoId;
    private LocalDateTime publishedAt;
    private String title;
    private String description;
    private String thumbnailUrl;
}
