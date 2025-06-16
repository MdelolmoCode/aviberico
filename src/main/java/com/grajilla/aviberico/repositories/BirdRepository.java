package com.grajilla.aviberico.repositories;

import com.grajilla.aviberico.entities.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BirdRepository extends JpaRepository<Bird, Long> {

    // buscar por nombre
    List<Bird> findByCommonNameContainsIgnoreCase(String commonName);

    // buscar por familia
    List<Bird> findByFamily(String family);

    // obtener familias únicas
    @Query("SELECT DISTINCT b.family FROM Bird b WHERE b.family IS NOT NULL ORDER BY b.family")
    List<String> findDistinctFamilies();

    /**
     * SELECT = SELECCIONAR/OBTENER
     * DISTINCT = DISTINTO/VALORES ÚNICOS
     * b.family = el atributo family de la entidad Bird
     * FROM Bird b = DESDE la entidad Bird
     * WHERE = DONDE
     * b.family IS NOT NULL = el atributo family NO ES NULO
     * ORDER BY b.family = ORDENAR POR/ordena alfabéticamente por family
     */
}