package com.beemer.fromis9server.youtube.scheduler;

import com.beemer.fromis9server.youtube.model.YouTubeVideoList;
import com.beemer.fromis9server.youtube.repository.YouTubeVideoRepository;
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
    private final YouTubeVideoRepository youTubeVideoRepository;

    @Autowired
    public YouTubeScheduler(YouTubeService youTubeService, YouTubeVideoRepository youTubeVideoRepository) {
        this.youTubeService = youTubeService;
        this.youTubeVideoRepository = youTubeVideoRepository;
    }

    @Scheduled(cron = "0 1,11,21,31,41,51 * * * *")
    public void fetchYouTubeVideoList() {
        String playlistId = "UU8qO5racajmy4YgPgNJkVXg";
        fetchPlaylist(playlistId);
    }

    @Scheduled(cron = "0 1 18 * * *")
    public void fetchYouTubeMVList() {
        String playlistId = "PLfO7cKwA7IXXZN5fuDSkb8vTmSaG_NzHO";
        fetchPlaylist(playlistId);
    }

    private void fetchPlaylist(String playlistId) {
        String nextPageToken = null;
        try {
            do {
                PlaylistItemListResponse response = youTubeService.getVideoList(playlistId, nextPageToken);
                for (PlaylistItem item : response.getItems()) {
                    processPlaylistItem(item);
                }
                nextPageToken = response.getNextPageToken();
            } while (nextPageToken != null && !nextPageToken.isEmpty());
        } catch (IOException ignored) {
        }
    }

    private void processPlaylistItem(PlaylistItem item) {
        String videoId = item.getContentDetails().getVideoId();
        YouTubeVideoList entity = youTubeVideoRepository.findById(videoId).orElseGet(YouTubeVideoList::new);

        entity.setVideoId(videoId);
        entity.setTitle(item.getSnippet().getTitle());
        entity.setDescription(item.getSnippet().getDescription());

        DateTime dateTime = item.getContentDetails().getVideoPublishedAt();
        Instant instant = Instant.ofEpochMilli(dateTime.getValue());
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
        LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        entity.setPublishedAt(localDateTime);

        if (item.getSnippet().getThumbnails().getMaxres() != null) {
            entity.setThumbnailUrl(item.getSnippet().getThumbnails().getMaxres().getUrl());
        } else if (item.getSnippet().getThumbnails().getMedium() != null) {
            entity.setThumbnailUrl(item.getSnippet().getThumbnails().getMedium().getUrl());
        }

        youTubeVideoRepository.save(entity);
    }
}