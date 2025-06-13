package com.grajilla.aviberico.controllers;

import com.grajilla.aviberico.entities.Bird;
import com.grajilla.aviberico.repositories.BirdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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

        // pasar datos a la vista
        model.addAttribute("birds", birds);

        return "bird/bird-list";
    }
}