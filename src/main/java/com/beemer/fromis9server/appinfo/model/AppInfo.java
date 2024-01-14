package com.beemer.fromis9server.appinfo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "AppInfo")
public class AppInfo {
    @Id
    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "release", nullable = false)
    private LocalDate release;

    @Column(name = "changelog", nullable = false)
    private String changelog;
}

