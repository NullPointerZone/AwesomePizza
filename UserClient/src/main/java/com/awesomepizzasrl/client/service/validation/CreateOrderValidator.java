package com.awesomepizzasrl.client.service.validation;


import com.awesomepizzasrl.client.exception.RequestException;
import com.awesomepizzasrl.client.model.CreateOrder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderValidator {

    private static final String USERNAME_ERROR = "Username must not be empty";
    private static final String PIZZA_ERROR = "Pizza list is invalid";

    public void validate(CreateOrder order) {
        if (order.getUsername() == null || order.getUsername().isBlank()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, USERNAME_ERROR);
        }

        if (order.getPizzas() == null || order.getPizzas().isEmpty()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, PIZZA_ERROR);
        }

        boolean invalidPizza = order.getPizzas().stream()
                .anyMatch(p -> p.getQuantity() <= 0 || p.getType() == null || p.getType().isBlank());

        if (invalidPizza) {
            throw new RequestException(HttpStatus.BAD_REQUEST, PIZZA_ERROR);
        }
    }
}
