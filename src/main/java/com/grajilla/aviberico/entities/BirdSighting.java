package com.grajilla.aviberico.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bird_sightings")
public class BirdSighting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // muchos avistamientos pueden corresponder a una misma ave
    @ManyToOne
    @JoinColumn(name = "bird_id", nullable = false)
    private Bird bird;

    // private Pajarero pajarero;

    private LocalDateTime dateTime;

    private String location;
}