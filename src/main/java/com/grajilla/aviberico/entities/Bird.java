package com.grajilla.aviberico.entities;

import com.grajilla.aviberico.enums.ConservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"habitats", "image"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "birds")
public class Bird {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // esta columna no puede ser null
    private String commonName;

    private String species;

    private String genus;

    private String family;

    @Column(name = "bird_order") // "order" es palabra reservada en SQL
    private String order;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING) // guarda el enum como String en BD en lugar de como número ordinal
    private ConservationStatus conservationStatus;

    // cada ave puede tener UNA imagen
    @OneToOne
    @JoinColumn(name = "image_id") // esta entidad tendrá una columna "image_id" como clave foránea
    private Image image;

    // un ave puede vivir en VARIOS hábitats y un hábitat puede tener VARIAS aves
    @ManyToMany
    @JoinTable(
            name = "bird_habitat", // nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "bird_id"), // columna que referencia a Bird
            inverseJoinColumns = @JoinColumn(name = "habitat_id") // columna que referencia a Habitat
    )
    @Builder.Default // asigna este valor por defecto al usar el builder
    private Set<Habitat> habitats = new HashSet<>(); // set para evitar duplicados
}