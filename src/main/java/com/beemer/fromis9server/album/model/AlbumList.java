package com.beemer.fromis9server.album.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "AlbumList")
public class AlbumList {
    @Id
    @Column(name = "albumName", nullable = false)
    private String albumName;

    @Column(name = "albumType", nullable = false)
    private String albumType;

    @Column(name = "releaseDate", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "colorPrimary", nullable = false)
    private String colorPrimary;

    @Column(name = "colorSecondary", nullable = false)
    private String colorSecondary;

    @OneToOne(mappedBy = "albumList", cascade = CascadeType.ALL)
    private AlbumArt albumArt;

    @OneToOne(mappedBy = "albumList", cascade = CascadeType.ALL)
    private AlbumDescription albumDescription;

    @OneToMany(mappedBy = "albumList", cascade = CascadeType.ALL)
    private List<SongList> songList;
}