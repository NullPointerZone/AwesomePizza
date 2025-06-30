package com.awesomepizzasrl.client.db.mapper;

import com.awesomepizzasrl.platform.db.entity.OrderEntity;
import com.awesomepizzasrl.platform.db.entity.OrderPizzaEntity;
import com.awesomepizzasrl.platform.service.dto.PizzaDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderPizzaEntityMapper {

    public Set<OrderPizzaEntity> map(List<PizzaDto> other, OrderEntity order){
        return other.stream()
                .map(p-> this.map(p, order))
                .collect(Collectors.toSet());
    }

    public OrderPizzaEntity map(PizzaDto other, OrderEntity order){
        return new OrderPizzaEntity(UUID.randomUUID(),other.getType(), other.getQuantity(), order);
    }


}
