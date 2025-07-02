package com.awesomepizzasrl.platform.controller;

import com.awesomepizzasrl.platform.model.PizzaVariantRequest;
import com.awesomepizzasrl.platform.service.PizzaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pizza")
@RequiredArgsConstructor
@Slf4j
public class PizzaController {

    private final PizzaService pizzaService;

    @GetMapping
    public ResponseEntity<?> getVariants(){
        try {
            return ResponseEntity
                    .ok()
                    .body(pizzaService.getVariants());
        }catch (Exception ex){
            return ResponseEntity
                    .status(500)
                    .body("Unexpected server error");
        }
    }

    @PostMapping("/addVariant")
    public ResponseEntity<String> addPizzaVariant(@RequestBody PizzaVariantRequest request) {
        try {
            log.info("Add new variant {}", request);
            return ResponseEntity
                    .ok()
                    .body(pizzaService.addVariant(request.getType()));
        }catch (Exception ex){
            log.error("{}", request, ex);
            return ResponseEntity
                    .status(500)
                    .body("Unexpected server error");
        }
    }
}