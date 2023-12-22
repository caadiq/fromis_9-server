package com.beemer.fromis9server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "platform_icon")
public class PlatformIcon {
    @Id
    private String platform;

    @Column(name = "image_url")
    private String imageUrl;
}