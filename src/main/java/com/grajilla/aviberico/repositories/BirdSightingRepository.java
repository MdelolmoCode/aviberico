package com.grajilla.aviberico.repositories;

import com.grajilla.aviberico.entities.BirdSighting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BirdSightingRepository extends JpaRepository<BirdSighting, Long> {

    List<BirdSighting> findByBird_Id(Long id);
}