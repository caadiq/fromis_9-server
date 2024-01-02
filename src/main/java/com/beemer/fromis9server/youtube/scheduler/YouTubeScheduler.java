package com.beemer.fromis9server.youtube.scheduler;

import com.beemer.fromis9server.youtube.model.YouTubeVideoList;
import com.beemer.fromis9server.youtube.repository.YouTubeRepository;
import com.beemer.fromis9server.youtube.service.YouTubeService;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class YouTubeScheduler {
    private final YouTubeService youTubeService;
    private final YouTubeRepository youTubeRepository;

    @Autowired
    public YouTubeScheduler(YouTubeService youTubeService, YouTubeRepository youTubeRepository) {
        this.youTubeService = youTubeService;
        this.youTubeRepository = youTubeRepository;
    }

    @Scheduled(cron = "0 1,11,21,31,41,51 * * * *")
    public void fetchYouTubeVideoList() {
        String playlistId = "UU8qO5racajmy4YgPgNJkVXg";
        String nextPageToken = null;

        try {
            do {
                PlaylistItemListResponse response = youTubeService.getVideoList(playlistId, nextPageToken);
                for (PlaylistItem item : response.getItems()) {
                    String videoId = item.getContentDetails().getVideoId();
                    YouTubeVideoList youTubeVideoList = youTubeRepository.findByVideoId(videoId).orElse(new YouTubeVideoList());
                    youTubeVideoList.setVideoId(videoId);
                    youTubeVideoList.setTitle(item.getSnippet().getTitle());
                    youTubeVideoList.setDescription(item.getSnippet().getDescription());
                    DateTime dateTime = item.getContentDetails().getVideoPublishedAt();
                    Instant instant = Instant.ofEpochMilli(dateTime.getValue());
                    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
                    LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();
                    youTubeVideoList.setPublishedAt(localDateTime);
                    if (item.getSnippet().getThumbnails().getMaxres() != null) {
                        youTubeVideoList.setThumbnailUrl(item.getSnippet().getThumbnails().getMaxres().getUrl());
                    } else {
                        youTubeVideoList.setThumbnailUrl(item.getSnippet().getThumbnails().getMedium().getUrl());
                    }
                    youTubeRepository.save(youTubeVideoList);
                }

                nextPageToken = response.getNextPageToken();
            } while (nextPageToken != null && !nextPageToken.isEmpty());
        } catch (IOException ignored) {

        }
    }
}