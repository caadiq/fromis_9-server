package com.beemer.fromis9server.album.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "PhotoList")
public class PhotoList {
    @EmbeddedId
    private PhotoListId photoListId;

    @ManyToOne
    @JoinColumn(name = "albumName", referencedColumnName = "albumName", insertable = false, updatable = false)
    private AlbumList albumList;

    @OneToMany(mappedBy = "photoList", cascade = CascadeType.ALL)
    private List<Photo> photo;
}
