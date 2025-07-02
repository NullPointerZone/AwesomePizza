package com.awesomepizzasrl.platform.db.mapper;

import com.awesomepizzasrl.platform.db.entity.OrderEntity;
import com.awesomepizzasrl.platform.db.entity.OrderPizzaEntity;
import com.awesomepizzasrl.platform.service.dto.CreateOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderEntityMapper {

    private final OrderPizzaEntityMapper orderPizzaEntityMapper;

    public OrderEntity map(CreateOrderDto other){
        OrderEntity result = new OrderEntity();
        result.setId(other.getIdOrder());
        result.setUsername(other.getUsername());
        result.setStatus(other.getStatus());
        Set<OrderPizzaEntity> pizzas = orderPizzaEntityMapper.map(other.getPizzas(), result);
        result.setPizzas(pizzas);
        return result;
    }
}
