package com.beemer.fromis9server.album.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "AlbumArt")
public class AlbumArt {
    @Id
    @Column(name = "albumName", nullable = false)
    private String albumName;

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "albumName", referencedColumnName = "albumName")
    private AlbumList albumList;
}