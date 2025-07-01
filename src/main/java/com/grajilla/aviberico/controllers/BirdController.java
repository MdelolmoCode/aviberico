package com.grajilla.aviberico.controllers;

import com.grajilla.aviberico.entities.*;
import com.grajilla.aviberico.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BirdController {

    private final BirdRepository birdRepository;
    private final HabitatRepository habitatRepository;
    private final ImageRepository imageRepository;
    private final BirdSightingRepository birdSightingRepository;
    private final PajareroRepository pajareroRepository;

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

    @GetMapping("/bird/new")
    public String showCreateForm(Model model) {
        // Crear objeto ave vacío para el formulario
        model.addAttribute("bird", new Bird());
        // Cargar todos los hábitats disponibles para seleccionar
        model.addAttribute("habitats", habitatRepository.findAll());

        // Mostrar formulario de creación
        return "bird/bird-form";
    }

    @GetMapping("/birds/{id}/edit")
    public String showEditForm(Model model, @PathVariable Long id) {
        Optional<Bird> birdOpt = birdRepository.findById(id);

        if (birdOpt.isPresent()) {
            model.addAttribute("bird", birdOpt.get());
            model.addAttribute("habitats", habitatRepository.findAll());
        } else {
            model.addAttribute("error", "Ave no encontrada");
        }

        return "bird/bird-form";
    }

    @PostMapping("/birds")
    public String saveForm(@ModelAttribute Bird bird,
                           @RequestParam(required = false) String newImageUrl,
                           @RequestParam(required = false) List<Long> habitatIds) {

        if (newImageUrl != null && !newImageUrl.trim().isEmpty()) {
            Image image = new Image();
            image.setImgUrl(newImageUrl);
            imageRepository.save(image);
            bird.setImage(image);
        }

        if (habitatIds != null) {
            Set<Habitat> habitats = new HashSet<>();
            habitatIds.forEach(id ->
                    habitatRepository.findById(id).ifPresent(habitats::add)
            );
            bird.setHabitats(habitats);
        }

        birdRepository.save(bird);
        return "redirect:/birds";
    }

    @PostMapping("/birds/{id}/delete")
    public String delete(@PathVariable Long id) {
        // Buscar ave a eliminar
        Optional<Bird> birdOpt = birdRepository.findById(id);

        if (birdOpt.isPresent()) {
            Bird bird = birdOpt.get();

            List<BirdSighting> sightings = birdSightingRepository.findByBird_Id(id);
            birdSightingRepository.deleteAll(sightings);

            List<Pajarero> pajareros = pajareroRepository.findAll();
            pajareros.forEach(p -> {
                if (p.getFavoriteBird() != null && p.getFavoriteBird().getId().equals(id)) {
                    p.setFavoriteBird(null);
                    pajareroRepository.save(p);
                }
            });

            bird.getHabitats().clear();
            birdRepository.save(bird);

            birdRepository.deleteById(id);
        }

        return "redirect:/birds";
    }
}