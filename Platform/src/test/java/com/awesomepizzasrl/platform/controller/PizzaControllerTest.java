package com.awesomepizzasrl.platform.controller;

import com.awesomepizzasrl.platform.db.entity.PizzaVariantEntity;
import com.awesomepizzasrl.platform.model.PizzaVariantRequest;
import com.awesomepizzasrl.platform.service.PizzaService;
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
    PizzaService pizzaService;
    @InjectMocks
    PizzaController pizzaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getVariants_whenServiceReturnsList_thenReturnsOkAndBody() {
        List<PizzaVariantEntity> variants = List.of( new PizzaVariantEntity("Margherita"), new PizzaVariantEntity("Capricciosa"));
        when(pizzaService.getVariants()).thenReturn(variants);

        ResponseEntity<?> response = pizzaController.getVariants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(variants, response.getBody());
        verify(pizzaService, times(1)).getVariants();
    }

    @Test
    void getVariants_whenServiceThrowsException_thenReturns500() {
        when(pizzaService.getVariants()).thenThrow(new RuntimeException("fail"));

        ResponseEntity<?> response = pizzaController.getVariants();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected server error", response.getBody());
        verify(pizzaService, times(1)).getVariants();
    }

    @Test
    void addPizzaVariant_whenValidRequest_thenReturnsOkAndBody() {
        PizzaVariantRequest request = new PizzaVariantRequest();
        request.setType("Margherita");

        when(pizzaService.addVariant("Margherita")).thenReturn("Variant added");

        ResponseEntity<String> response = pizzaController.addPizzaVariant(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Variant added", response.getBody());
        verify(pizzaService, times(1)).addVariant("Margherita");
    }

    @Test
    void addPizzaVariant_whenServiceThrowsException_thenReturns500() {
        PizzaVariantRequest request = new PizzaVariantRequest();
        request.setType("Margherita");

        when(pizzaService.addVariant("Margherita")).thenThrow(new RuntimeException("fail"));

        ResponseEntity<String> response = pizzaController.addPizzaVariant(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected server error", response.getBody());
        verify(pizzaService, times(1)).addVariant("Margherita");
    }
}