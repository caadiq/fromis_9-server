package com.beemer.fromis9server.album.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class PhotoListId implements Serializable {
    @Column(name = "id")
    private int id;

    @Column(name = "albumName")
    private String albumName;
}
