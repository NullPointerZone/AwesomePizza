package com.awesomepizzasrl.platform.service.mapper;

import awesomepizzasrl.eventmodel.model.CreateOrder;
import com.awesomepizzasrl.platform.service.dto.CreateOrderDto;
import awesomepizzasrl.eventmodel.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CreateOrderDtoMapper {

    private final PizzaDtoMapper pizzaDtoMapper;

    public CreateOrderDto map(CreateOrder order, OrderStatus status){
        return new CreateOrderDto(order.getIdOrder(), order.getUsername(), pizzaDtoMapper.map(order.getPizzas()), status);
    }
}
