package com.grajilla.aviberico.entities;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = {"bird", "pajarero"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    // MUCHOS avistamientos pueden ser del MISMO pajarero
    @ManyToOne
    @JoinColumn(name = "pajarero_id", nullable = false)
    private Pajarero pajarero;

    private LocalDateTime dateTime;

    private String location;
}