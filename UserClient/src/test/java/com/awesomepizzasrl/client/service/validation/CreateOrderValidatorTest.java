package com.awesomepizzasrl.client.service.validation;

import com.awesomepizzasrl.client.exception.RequestException;
import com.awesomepizzasrl.client.model.CreateOrder;
import com.awesomepizzasrl.client.model.Pizza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateOrderValidatorTest {

    @InjectMocks
    CreateOrderValidator validator;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validate_whenValidOrder_thenDoesNotThrow() {
        CreateOrder order = new CreateOrder();
        order.setUsername("user123");
        order.setPizzas(List.of(new Pizza("Margherita", 2)));

        assertDoesNotThrow(() -> validator.validate(order));
    }

    @Test
    void validate_whenUsernameNull_thenThrowRequestException() {
        CreateOrder order = new CreateOrder();
        order.setUsername(null);
        order.setPizzas(List.of(new Pizza("Margherita", 2)));

        RequestException ex = assertThrows(RequestException.class, () -> validator.validate(order));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getCode());
        assertEquals("Username must not be empty", ex.getBody());
    }

    @Test
    void validate_whenUsernameBlank_thenThrowRequestException() {
        CreateOrder order = new CreateOrder();
        order.setUsername("   ");
        order.setPizzas(List.of(new Pizza("Margherita", 1)));

        RequestException ex = assertThrows(RequestException.class, () -> validator.validate(order));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getCode());
        assertEquals("Username must not be empty", ex.getBody());
    }

    @Test
    void validate_whenPizzasNull_thenThrowRequestException() {
        CreateOrder order = new CreateOrder();
        order.setUsername("user");
        order.setPizzas(null);

        RequestException ex = assertThrows(RequestException.class, () -> validator.validate(order));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getCode());
        assertEquals("Pizza list is invalid", ex.getBody());
    }

    @Test
    void validate_whenPizzasEmpty_thenThrowRequestException() {
        CreateOrder order = new CreateOrder();
        order.setUsername("user");
        order.setPizzas(List.of());

        RequestException ex = assertThrows(RequestException.class, () -> validator.validate(order));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getCode());
        assertEquals("Pizza list is invalid", ex.getBody());
    }

    @Test
    void validate_whenPizzaHasInvalidQuantity_thenThrowRequestException() {
        CreateOrder order = new CreateOrder();
        order.setUsername("user");
        order.setPizzas(List.of(new Pizza("Margherita", 0)));

        RequestException ex = assertThrows(RequestException.class, () -> validator.validate(order));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getCode());
        assertEquals("Pizza list is invalid", ex.getBody());
    }

    @Test
    void validate_whenPizzaHasNullType_thenThrowRequestException() {
        CreateOrder order = new CreateOrder();
        order.setUsername("user");
        order.setPizzas(List.of(new Pizza(null, 1)));

        RequestException ex = assertThrows(RequestException.class, () -> validator.validate(order));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getCode());
        assertEquals("Pizza list is invalid", ex.getBody());
    }

    @Test
    void validate_whenPizzaHasBlankType_thenThrowRequestException() {
        CreateOrder order = new CreateOrder();
        order.setUsername("user");
        order.setPizzas(List.of(new Pizza("", 1)));

        RequestException ex = assertThrows(RequestException.class, () -> validator.validate(order));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getCode());
        assertEquals("Pizza list is invalid", ex.getBody());
    }
}