package com.awesomepizzasrl.platform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    @PostMapping("/addVariant")
    public ResponseEntity<String> addPizzaVariant(@RequestBody String request) {
        // Qui puoi aggiungere la logica di salvataggio nel database o in memoria
        System.out.println("Ricevuta nuova variante di pizza: " + request);
        return ResponseEntity.ok().build();
    }
}