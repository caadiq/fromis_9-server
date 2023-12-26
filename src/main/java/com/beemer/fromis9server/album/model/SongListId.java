package com.beemer.fromis9server.album.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SongListId implements Serializable {
    @Column(name = "songName")
    private String songName;

    @Column(name = "albumName")
    private String albumName;
}