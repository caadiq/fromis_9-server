package com.beemer.fromis9server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String classify;

    @Column(nullable = false)
    private String colorPrimary;

    @Column(nullable = false)
    private String colorSecondary;

    @ManyToOne
    @JoinColumn(name = "album", referencedColumnName = "album")
    private AlbumArt albumArt;
}
