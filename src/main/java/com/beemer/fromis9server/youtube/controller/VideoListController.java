package com.beemer.fromis9server.youtube.controller;

import com.beemer.fromis9server.youtube.service.VideoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fromis9")
public class VideoListController {
    private final VideoListService videoListService;

    @Autowired
    public VideoListController(VideoListService videoListService) {
        this.videoListService = videoListService;
    }

    @GetMapping("/videolist")
    public ResponseEntity<?> getVideoList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "publishedAt") String sort) {

        limit = Math.max(1, Math.min(limit, 50));
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort).descending());

        return videoListService.getVideoList(pageable);
    }
}