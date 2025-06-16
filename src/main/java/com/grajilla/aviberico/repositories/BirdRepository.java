package com.grajilla.aviberico.repositories;

import com.grajilla.aviberico.entities.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BirdRepository extends JpaRepository<Bird, Long> {

    // buscar por familia
    List<Bird> findByFamily(String family);

    // obtener familias únicas
    @Query("SELECT DISTINCT b.family FROM Bird b WHERE b.family IS NOT NULL ORDER BY b.family")
    List<Bird> findDistincFamilies();
}