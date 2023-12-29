package com.beemer.fromis9server.album.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Song")
public class Song {
    @EmbeddedId
    private TrackListId trackListId;

    @Column(name = "lyricist", nullable = false)
    private String lyricist;

    @Column(name = "composer", nullable = false)
    private String composer;

    @Column(name = "arranger")
    private String arranger;

    @Column(name = "lyrics", nullable = false)
    private String lyrics;

    @Column(name = "songLength", nullable = false)
    private String songLength;

    @Column(name = "titleTrack", nullable = false)
    private boolean titleTrack;

    @Column(name = "trackNumber", nullable = false)
    private int trackNumber;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "songName", referencedColumnName = "songName", insertable = false, updatable = false),
        @JoinColumn(name = "albumName", referencedColumnName = "albumName", insertable = false, updatable = false)
    })
    private TrackList trackList;
}