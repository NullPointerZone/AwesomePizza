package com.awesomepizzasrl.platform.controller;

import com.awesomepizzasrl.client.exception.RequestException;
import com.awesomepizzasrl.platform.service.PizzaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pizza")
@RequiredArgsConstructor
@Slf4j
public class PizzaController {

    private final PizzaService pizzaService;

    @PostMapping("/addVariant")
    public ResponseEntity<String> addPizzaVariant(@RequestBody String request) {
        try {
            log.info("{}", request);
            pizzaService.addVariant(request);
            return ResponseEntity
                    .ok()
                    .body(request);
        }catch (Exception ex){
            log.error("{}", request, ex);
            return ResponseEntity
                    .status(500)
                    .body("Unexpected server error");
        }
    }
}