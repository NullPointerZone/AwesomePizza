package com.awesomepizzasrl.client.controller;

import com.awesomepizzasrl.client.db.entity.PizzaVariantEntity;
import com.awesomepizzasrl.client.service.PizzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PizzaControllerTest {

    @Mock
    private PizzaService pizzaService;

    @InjectMocks
    private PizzaController pizzaController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPizzaList_whenServiceReturnsVariants_thenReturn200() {
        List<PizzaVariantEntity> variants = List.of(new PizzaVariantEntity("Margherita"),new PizzaVariantEntity( "Pepperoni"), new PizzaVariantEntity("Quattro Stagioni"));

        when(pizzaService.getVariants()).thenReturn(variants);

        ResponseEntity<?> response = pizzaController.getPizzaList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(variants, response.getBody());
    }

    @Test
    void getPizzaList_whenServiceThrowsException_thenReturn500() {
        when(pizzaService.getVariants()).thenThrow(new RuntimeException("Service failure"));

        ResponseEntity<?> response = pizzaController.getPizzaList();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected server error", response.getBody());
    }
}