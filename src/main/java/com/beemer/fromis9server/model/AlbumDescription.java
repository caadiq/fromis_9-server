package com.beemer.fromis9server.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "AlbumDescription")
public class AlbumDescription {
    @Id
    @Column(name = "albumName", nullable = false)
    private String albumName;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "albumName", referencedColumnName = "albumName")
    private AlbumList albumList;
}
