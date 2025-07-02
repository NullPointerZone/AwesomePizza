package com.awesomepizzasrl.platform.service;

import com.awesomepizzasrl.platform.db.entity.PizzaVariantEntity;
import com.awesomepizzasrl.platform.db.repository.PizzaVariantRepository;
import com.awesomepizzasrl.platform.exception.RequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PizzaServiceTest {
    @Mock
    PizzaVariantRepository pizzaVariantRepository;
    @InjectMocks
    PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addVariant_whenValidRequest_shouldSaveAndReturnVariant() {
        String variantName = "Margherita";

        String result = pizzaService.addVariant(variantName);

        assertEquals(variantName, result);
        verify(pizzaVariantRepository).save(any(PizzaVariantEntity.class));
    }

    @Test
    void addVariant_whenNull_shouldThrowRequestException() {
        RequestException exception = assertThrows(
                RequestException.class,
                () -> pizzaService.addVariant(null)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getCode());
        assertEquals("Variant name not valid", exception.getBody());
        verifyNoInteractions(pizzaVariantRepository);
    }

    @Test
    void addVariant_whenBlank_shouldThrowRequestException() {
        RequestException exception = assertThrows(
                RequestException.class,
                () -> pizzaService.addVariant("   ")
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getCode());
        assertEquals("Variant name not valid", exception.getBody());
        verifyNoInteractions(pizzaVariantRepository);
    }

    @Test
    void getVariants_shouldReturnListFromRepository() {
        List<PizzaVariantEntity> variants = List.of(
                new PizzaVariantEntity("Margherita"),
                new PizzaVariantEntity("Diavola")
        );
        when(pizzaVariantRepository.findAll()).thenReturn(variants);

        List<PizzaVariantEntity> result = pizzaService.getVariants();

        assertEquals(2, result.size());
        assertEquals("Margherita", result.get(0).getPizzaType());
        assertEquals("Diavola", result.get(1).getPizzaType());
    }

    @Test
    void getVariants_whenEmpty_shouldReturnEmptyList() {
        when(pizzaVariantRepository.findAll()).thenReturn(List.of());

        List<PizzaVariantEntity> result = pizzaService.getVariants();

        assertTrue(result.isEmpty());
    }
}