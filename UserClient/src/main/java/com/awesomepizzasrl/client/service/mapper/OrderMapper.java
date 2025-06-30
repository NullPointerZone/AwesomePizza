package com.awesomepizzasrl.client.service.mapper;

import com.awesomepizzasrl.client.model.CreateOrder;
import com.awesomepizzasrl.client.model.Pizza;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderMapper {

    public awesomepizzasrl.eventmodel.model.CreateOrder toCreateOrder(CreateOrder other){
        return new awesomepizzasrl.eventmodel.model.CreateOrder(UUID.randomUUID(), other.getUsername(), this.toPizza(other.getPizzas()));
    }

    public List<awesomepizzasrl.eventmodel.model.Pizza> toPizza(List<Pizza> others) {
        return others.stream()
                .map(this::toPizza)
                .toList();
    }

    public awesomepizzasrl.eventmodel.model.Pizza toPizza(Pizza other){
        return new awesomepizzasrl.eventmodel.model.Pizza(other.getType(), other.getQuantity());
    }

}
