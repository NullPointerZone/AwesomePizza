package com.awesomepizzasrl.platform.service.mapper;

import awesomepizzasrl.eventmodel.model.Pizza;
import com.awesomepizzasrl.platform.service.dto.PizzaDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PizzaDtoMapper {

    public List<PizzaDto> map(List<Pizza> other) {
        return other.stream()
                .map(this::map)
                .toList();
    }

    public PizzaDto map(Pizza other){
        return new PizzaDto(other.getType(), other.getQuantity());
    }
}
