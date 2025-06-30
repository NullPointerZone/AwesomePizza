package com.awesomepizzasrl.platform.service.dto;

import com.awesomepizzasrl.platform.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateOrderDto {

    private UUID idOrder;
    private String username;
    private List<PizzaDto> pizzas;
    private OrderStatus status;

}
