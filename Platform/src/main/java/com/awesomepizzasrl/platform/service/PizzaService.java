package com.awesomepizzasrl.platform.service;

import com.awesomepizzasrl.platform.db.entity.PizzaVariantEntity;
import com.awesomepizzasrl.platform.db.repository.PizzaVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PizzaService {
    private final PizzaVariantRepository pizzaVariantRepository;

    public void addVariant(String request) {
        pizzaVariantRepository.save(new PizzaVariantEntity(request));
    }
}
