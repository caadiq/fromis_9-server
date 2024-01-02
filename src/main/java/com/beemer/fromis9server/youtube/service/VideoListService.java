package com.beemer.fromis9server.youtube.service;

import com.beemer.fromis9server.youtube.dto.VideoDTO;
import com.beemer.fromis9server.youtube.dto.VideoListDTO;
import com.beemer.fromis9server.youtube.dto.VideoPageDTO;
import com.beemer.fromis9server.youtube.model.YouTubeVideoList;
import com.beemer.fromis9server.youtube.repository.YouTubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoListService {
    private final YouTubeRepository youTubeRepository;

    @Autowired
    public VideoListService(YouTubeRepository youTubeRepository) {
        this.youTubeRepository = youTubeRepository;
    }

    public ResponseEntity<?> getVideoList(Pageable pageable) {
        Page<YouTubeVideoList> page = youTubeRepository.findAll(pageable);

        if (page.getContent().isEmpty() && page.getTotalElements() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("영상이 없습니다.");
        }

        List<VideoDTO> videoDTOs = page.getContent().stream()
                .map(video -> new VideoDTO(
                        video.getVideoId(),
                        video.getPublishedAt(),
                        video.getTitle(),
                        video.getDescription(),
                        video.getThumbnailUrl()))
                .collect(Collectors.toList());

        Integer prevPage = page.hasPrevious() ? page.getNumber() - 1 : null;
        Integer nextPage = page.hasNext() ? page.getNumber() + 1 : null;

        VideoPageDTO pageDTO = new VideoPageDTO(prevPage, page.getNumber(), nextPage);

        return ResponseEntity.ok(new VideoListDTO(pageDTO, videoDTOs));
    }
}