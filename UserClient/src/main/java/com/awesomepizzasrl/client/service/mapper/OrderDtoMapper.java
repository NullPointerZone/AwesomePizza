package com.awesomepizzasrl.client.service.mapper;

import com.awesomepizzasrl.client.db.entity.OrderEntity;
import com.awesomepizzasrl.client.service.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper {
    public OrderDto map(OrderEntity entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}