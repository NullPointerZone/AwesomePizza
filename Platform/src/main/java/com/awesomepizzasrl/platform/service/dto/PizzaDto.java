package com.awesomepizzasrl.platform.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PizzaDto {
    private String type;
    private int quantity;
}
