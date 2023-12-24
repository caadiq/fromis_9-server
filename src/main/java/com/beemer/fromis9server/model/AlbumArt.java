package com.beemer.fromis9server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "album_art")
public class AlbumArt {
    @Id
    private String album;

    @Column(name = "image_url")
    private String imageUrl;
}
