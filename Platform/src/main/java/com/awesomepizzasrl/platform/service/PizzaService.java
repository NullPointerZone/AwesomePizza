package com.awesomepizzasrl.platform.service;

import com.awesomepizzasrl.platform.db.entity.PizzaVariantEntity;
import com.awesomepizzasrl.platform.db.repository.PizzaVariantRepository;
import com.awesomepizzasrl.platform.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaService {
    private final PizzaVariantRepository pizzaVariantRepository;

    public String addVariant(String request) {
        if(request == null || request.isBlank())
            throw new RequestException(HttpStatus.BAD_REQUEST, "Variant name not valid");

        pizzaVariantRepository.save(new PizzaVariantEntity(request));
        return request;
    }

    public List<PizzaVariantEntity> getVariants() {
        return pizzaVariantRepository.findAll();
    }
}
