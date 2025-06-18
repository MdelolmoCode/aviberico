package com.grajilla.aviberico.controllers;

import com.grajilla.aviberico.entities.Bird;
import com.grajilla.aviberico.repositories.BirdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BirdController {

    private final BirdRepository birdRepository;

    @GetMapping("/birds")
    public String findAll(Model model) {
        log.info("Buscar todas las aves para la vista");

        // obtener todas las aves de la base de datos
        List<Bird> birds = birdRepository.findAll();
        List<String> familias = birdRepository.findDistinctFamilies();

        // pasar datos a la vista
        model.addAttribute("birds", birds);
        model.addAttribute("familias", familias);

        return "bird/bird-list";
    }

    @GetMapping("/birds/buscar")
    public String findByCommonName(Model model, @RequestParam String nombre) {
        // buscar aves por nombre
        List<Bird> birds = birdRepository.findByCommonNameContainsIgnoreCase(nombre);
        // encontrar lista de familias
        List<String> familias = birdRepository.findDistinctFamilies();

        // pasar resultado de filtrado a la vista
        model.addAttribute("birds", birds);
        model.addAttribute("familias", familias);
        model.addAttribute("busquedaActual", nombre);

        return "bird/bird-list";
    }

    @GetMapping("/birds/familia/{familyName}")
    public String findByFamily(Model model, @PathVariable String familyName) {
        log.info("Filtrar aves por familia: {}", familyName);

        // buscar aves de la familia específica
        List<Bird> birds = birdRepository.findByFamily(familyName);
        // encontrar lista de familias
        List<String> familias = birdRepository.findDistinctFamilies();

        // pasar resultados de filtrado a vista
        model.addAttribute("birds", birds);
        model.addAttribute("familias", familias);
        model.addAttribute("familiaFiltrada", familyName);

        return "bird/bird-list";
    }

    @GetMapping("/birds/{id}")
    public String findById(Model model, @PathVariable Long id) {
        // buscar ave por id
        Optional<Bird> birdOpt = birdRepository.findById(id);

        // verificar si el ave existe
        if (birdOpt.isPresent()) {
            model.addAttribute("bird", birdOpt.get());
        } else {
            model.addAttribute("error", "Ave no encontrada");
        }

        return "bird/bird-detail";
    }
}