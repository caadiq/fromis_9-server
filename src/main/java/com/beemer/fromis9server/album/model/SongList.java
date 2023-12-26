package com.beemer.fromis9server.album.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "SongList")
public class SongList {
    @EmbeddedId
    private SongListId songListId;

    @ManyToOne
    @JoinColumn(name = "albumName", referencedColumnName = "albumName", insertable = false, updatable = false)
    private AlbumList albumList;

    @OneToMany(mappedBy = "songList", cascade = CascadeType.ALL)
    private List<Song> song;
}