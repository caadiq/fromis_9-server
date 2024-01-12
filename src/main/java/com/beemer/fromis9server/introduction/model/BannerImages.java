package com.beemer.fromis9server.introduction.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "BannerImages")
public class BannerImages {
    @Id
    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;
}
