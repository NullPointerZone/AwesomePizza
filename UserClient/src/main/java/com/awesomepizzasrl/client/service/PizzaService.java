package com.awesomepizzasrl.client.service;

import com.awesomepizzasrl.client.db.entity.PizzaVariantEntity;
import com.awesomepizzasrl.client.db.repository.PizzaVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaVariantRepository pizzaVariantRepository;

    public List<PizzaVariantEntity> getVariants() {
        return pizzaVariantRepository.findAll();
    }
}
