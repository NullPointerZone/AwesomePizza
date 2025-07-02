package com.awesomepizzasrl.client.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrder {
    private static final String PIZZA_ERROR = "Pizza order not valid";

    private static final String USERNAME_ERROR = "Username not valid";

    @JsonProperty("username")
    private String username;
    @JsonProperty("pizzas")
    private List<Pizza> pizzas;
}
