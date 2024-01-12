package com.beemer.fromis9server.introduction.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Members")
public class Members {
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "blood", nullable = false)
    private String blood;
}
