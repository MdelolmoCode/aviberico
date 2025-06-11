package com.grajilla.aviberico.entities;

import com.grajilla.aviberico.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"favoriteBird", "birdSightings"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pajareros")
public class Pajarero { // entidad usuario

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // email único para el login, no puede ser null
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    // muchos usuarios puede tener la misma ave favorita, pero sólo UNA
    @ManyToOne
    @JoinColumn(name = "favorite_bird_id")
    private Bird favoriteBird;

    // por defecto todos los usuarios están activos
    @Builder.Default
    private Boolean isActive = true;

    // guardo el rol como texto
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    // por defecto todos los usuarios son USER, no ADMIN
    @Builder.Default
    private UserRole role = UserRole.USER;

    // UN pajarero puede tener MUCHOS avistamientos
    // mappedBy indica que la relación está mapeada por el atributo "pajarero" en BirdSighting
    // cascade = ALL significa que si borramos un pajarero, se borran todos sus avistamientos
    @OneToMany(mappedBy = "pajarero", cascade = CascadeType.ALL)
    @Builder.Default
    private List<BirdSighting> birdSightings = new ArrayList<>();
}