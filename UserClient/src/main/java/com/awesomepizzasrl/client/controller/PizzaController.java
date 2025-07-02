package com.awesomepizzasrl.client.controller;


import com.awesomepizzasrl.client.service.PizzaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pizza")
@RequiredArgsConstructor
@Slf4j
public class PizzaController {

    private final PizzaService pizzaService;

    @GetMapping
    public ResponseEntity<?> getPizzaList() {
        try {
            return ResponseEntity
                    .ok()
                    .body(pizzaService.getVariants());
        }catch (Exception ex){
            log.error("", ex);
            return ResponseEntity
                    .status(500)
                    .body("Unexpected server error");
        }
    }

}
