package com.grajilla.aviberico.controllers;

import com.grajilla.aviberico.entities.Bird;
import com.grajilla.aviberico.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BirdRepository birdRepository;
    private final BirdSightingRepository birdSightingRepository;
    private final PajareroRepository pajareroRepository;
    private final HabitatRepository habitatRepository;

    @GetMapping("/")
    public String home(Model model) {
        log.info("Cargando página de inicio");

        try {
            long totalBirds = birdRepository.count();
            long totalSightings = birdSightingRepository.count();
            long totalUsers = pajareroRepository.count();
            long totalHabitats = habitatRepository.count();

            List<Bird> allBirds = birdRepository.findAll();
            List<Bird> latestBirds = allBirds.stream()
                    .limit(6)
                    .toList();

            List<String> topFamilies = birdRepository.findDistinctFamilies()
                    .stream()
                    .limit(5)
                    .toList();

            model.addAttribute("totalBirds", totalBirds);
            model.addAttribute("totalSightings", totalSightings);
            model.addAttribute("totalUsers", totalUsers);
            model.addAttribute("totalHabitats", totalHabitats);
            model.addAttribute("latestBirds", latestBirds);
            model.addAttribute("topFamilies", topFamilies);

            log.info("Datos cargados: {} aves, {} avistamientos, {} usuarios, {} hábitats",
                    totalBirds, totalSightings, totalUsers, totalHabitats);

        } catch (Exception e) {
            log.error("Error al cargar datos para la página de inicio", e);
            model.addAttribute("totalBirds", 0L);
            model.addAttribute("totalSightings", 0L);
            model.addAttribute("totalUsers", 0L);
            model.addAttribute("totalHabitats", 0L);
            model.addAttribute("latestBirds", List.of());
            model.addAttribute("topFamilies", List.of());
        }

        return "home";
    }
}