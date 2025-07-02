package com.awesomepizzasrl.client.service.dto;

import awesomepizzasrl.eventmodel.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private UUID id;
    private String username;
    private OrderStatus status;
}