package com.beemer.fromis9server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ScheduleList")
public class ScheduleList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "schedule", nullable = false)
    private String schedule;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "platform", referencedColumnName = "platform")
    private ScheduleIcon scheduleIcon;
}