package com.awesomepizzasrl.platform.service.validation;

import awesomepizzasrl.eventmodel.model.OrderStatus;
import com.awesomepizzasrl.platform.exception.OrderException;
import com.awesomepizzasrl.platform.service.dto.CreateOrderDto;
import com.awesomepizzasrl.platform.service.dto.PizzaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {

    @InjectMocks
    OrderValidator orderValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validate_whenValidOrder_doesNotThrow() {
        CreateOrderDto order = new CreateOrderDto(
                UUID.randomUUID(),
                "username",
                List.of(new PizzaDto("variant1", 1)),
                OrderStatus.PENDING
        );
        Set<String> variants = Set.of("variant1", "variant2");

        assertDoesNotThrow(() -> orderValidator.validate(order, variants));
    }

    @Test
    void validate_whenVariantsNull_throwsException() {
        CreateOrderDto order = new CreateOrderDto(
                UUID.randomUUID(),
                "username",
                List.of(new PizzaDto("variant1", 1)),
                OrderStatus.PENDING
        );

        OrderException ex = assertThrows(OrderException.class,
                () -> orderValidator.validate(order, null));

        assertEquals("Pizza variants not found", ex.getMessage());
    }

    @Test
    void validate_whenVariantsEmpty_throwsException() {
        CreateOrderDto order = new CreateOrderDto(
                UUID.randomUUID(),
                "username",
                List.of(new PizzaDto("variant1", 1)),
                OrderStatus.PENDING
        );

        OrderException ex = assertThrows(OrderException.class,
                () -> orderValidator.validate(order, Set.of()));

        assertEquals("Pizza variants not found", ex.getMessage());
    }

    @Test
    void validate_whenOrderNull_throwsException() {
        Set<String> variants = Set.of("variant1");

        OrderException ex = assertThrows(OrderException.class,
                () -> orderValidator.validate(null, variants));

        assertEquals("Order cannot be null", ex.getMessage());
    }

    @Test
    void validate_whenOrderIdNull_throwsException() {
        CreateOrderDto order = new CreateOrderDto(
                null,
                "username",
                List.of(new PizzaDto("variant1", 1)),
                OrderStatus.PENDING
        );
        Set<String> variants = Set.of("variant1");

        OrderException ex = assertThrows(OrderException.class,
                () -> orderValidator.validate(order, variants));

        assertEquals("Order ID cannot be null", ex.getMessage());
    }

    @Test
    void validate_whenUsernameNull_throwsException() {
        Set<String> variants = Set.of("variant1");

        CreateOrderDto orderNullUsername = new CreateOrderDto(
                UUID.randomUUID(),
                null,
                List.of(new PizzaDto("variant1", 1)),
                OrderStatus.PENDING
        );


        OrderException ex1 = assertThrows(OrderException.class,
                () -> orderValidator.validate(orderNullUsername, variants));
        assertEquals("Username cannot be null or blank", ex1.getMessage());
    }

    @Test
    void validate_whenUsernameBlank_throwsException() {
        Set<String> variants = Set.of("variant1");

        CreateOrderDto orderBlankUsername = new CreateOrderDto(
                UUID.randomUUID(),
                " ",
                List.of(new PizzaDto("variant1", 1)),
                OrderStatus.PENDING
        );

        OrderException ex2 = assertThrows(OrderException.class,
                () -> orderValidator.validate(orderBlankUsername, variants));
        assertEquals("Username cannot be null or blank", ex2.getMessage());
    }

    @Test
    void validate_whenPizzasNull_throwsException() {
        Set<String> variants = Set.of("variant1");

        CreateOrderDto orderNullPizzas = new CreateOrderDto(
                UUID.randomUUID(),
                "username",
                null,
                OrderStatus.PENDING
        );

        OrderException ex1 = assertThrows(OrderException.class,
                () -> orderValidator.validate(orderNullPizzas, variants));
        assertEquals("At least one pizza must be included in the order", ex1.getMessage());
    }

    @Test
    void validate_whenPizzasEmpty_throwsException() {
        Set<String> variants = Set.of("variant1");

        CreateOrderDto orderEmptyPizzas = new CreateOrderDto(
                UUID.randomUUID(),
                "username",
                List.of(),
                OrderStatus.PENDING
        );

        OrderException ex2 = assertThrows(OrderException.class,
                () -> orderValidator.validate(orderEmptyPizzas, variants));
        assertEquals("At least one pizza must be included in the order", ex2.getMessage());
    }

    @Test
    void validate_whenPizzaQuantityInvalid_throwsException() {
        Set<String> variants = Set.of("variant1");

        CreateOrderDto order = new CreateOrderDto(
                UUID.randomUUID(),
                "username",
                List.of(new PizzaDto("variant1", 0)),
                OrderStatus.PENDING
        );

        OrderException ex = assertThrows(OrderException.class,
                () -> orderValidator.validate(order, variants));

        assertEquals("Pizza quantity not valid", ex.getMessage());
    }

    @Test
    void validate_whenPizzaVariantNotInVariants_throwsException() {
        Set<String> variants = Set.of("variant1");

        CreateOrderDto order = new CreateOrderDto(
                UUID.randomUUID(),
                "username",
                List.of(new PizzaDto("invalidVariant", 1)),
                OrderStatus.PENDING
        );

        OrderException ex = assertThrows(OrderException.class,
                () -> orderValidator.validate(order, variants));

        assertEquals("Pizza variant not valid", ex.getMessage());
    }
}
