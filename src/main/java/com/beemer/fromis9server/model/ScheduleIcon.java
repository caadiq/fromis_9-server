package com.beemer.fromis9server.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ScheduleIcon")
public class ScheduleIcon {
    @Id
    @Column(name = "platform", nullable = false)
    private String platform;

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;
}