package com.beemer.fromis9server.album.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Photo")
public class Photo {
    @EmbeddedId
    private PhotoListId photoListId;

    @Column(name = "concept")
    private String concept;

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false),
        @JoinColumn(name = "albumName", referencedColumnName = "albumName", insertable = false, updatable = false)
    })
    private PhotoList photoList;
}