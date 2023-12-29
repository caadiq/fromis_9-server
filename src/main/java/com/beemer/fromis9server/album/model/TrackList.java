package com.beemer.fromis9server.album.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "TrackList")
public class TrackList {
    @EmbeddedId
    private TrackListId trackListId;

    @ManyToOne
    @JoinColumn(name = "albumName", referencedColumnName = "albumName", insertable = false, updatable = false)
    private AlbumList albumList;

    @OneToMany(mappedBy = "trackList", cascade = CascadeType.ALL)
    private List<Song> song;
}