package com.beemer.fromis9server.introduction.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "DebutDate")
public class DebutDate {
    @Id
    @Column(name = "debutDate", nullable = false)
    private LocalDate debutDate;
}
