package com.beemer.fromis9server.youtube.repository;

import com.beemer.fromis9server.youtube.model.YouTubeVideoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YouTubeRepository extends JpaRepository<YouTubeVideoList, String> {
    Optional<YouTubeVideoList> findByVideoId(String videoId);
}