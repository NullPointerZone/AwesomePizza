package com.awesomepizzasrl.client.model;


import com.awesomepizzasrl.client.exception.RequestException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class CreateOrder {
    private static final String PIZZA_ERROR = "Pizza order not valid";

    private static final String USERNAME_ERROR = "Username not valid";

    @JsonProperty("username")
    private String username;
    @JsonProperty("pizzas")
    private List<Pizza> pizzas;

    public void validate() {
        if(username == null || username.isBlank()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, USERNAME_ERROR);
        } else if (pizzas == null || pizzas.isEmpty()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, PIZZA_ERROR);
        }else {
            if (pizzas.stream().anyMatch(p -> p.getQuantity() <= 0 || p.getType() == null || p.getType().isBlank())) {
                throw  new RequestException(HttpStatus.BAD_REQUEST, PIZZA_ERROR);
            }
        }
    }
}
