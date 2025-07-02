package com.awesomepizzasrl.client.service.dto;

import awesomepizzasrl.eventmodel.model.OrderStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderStatusDto {
    private UUID id;
    private String username;
    private OrderStatus status;
}