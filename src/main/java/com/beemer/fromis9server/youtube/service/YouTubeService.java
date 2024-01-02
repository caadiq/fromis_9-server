package com.beemer.fromis9server.youtube.service;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.VideoListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
public class YouTubeService {
    @Value("${youtube.api.key:}")
    private String apiKey;

    public PlaylistItemListResponse getVideoList(String playlistId, String pageToken) throws IOException {
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        YouTube youtube = new YouTube.Builder(new com.google.api.client.http.javanet.NetHttpTransport(),
                jsonFactory, request -> {})
                .setApplicationName("Fromis9VideoListFetcher")
                .build();
        YouTube.PlaylistItems.List request = youtube.playlistItems().list(Collections.singletonList("snippet,contentDetails"));
        request.setKey(apiKey);
        request.setPlaylistId(playlistId);
        request.setMaxResults(50L);

        if (pageToken != null && !pageToken.isEmpty()) {
            request.setPageToken(pageToken);
        }

        return request.execute();
    }

    public VideoListResponse getVideoStatistics(String videoId) throws IOException {
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        YouTube youtube = new YouTube.Builder(new com.google.api.client.http.javanet.NetHttpTransport(),
                jsonFactory, request -> {})
                .setApplicationName("Fromis9VideoStatisticsFetcher")
                .build();
        YouTube.Videos.List request = youtube.videos().list(Collections.singletonList("statistics"));
        request.setKey(apiKey);
        request.setId(Collections.singletonList(videoId));

        return request.execute();
    }
}
