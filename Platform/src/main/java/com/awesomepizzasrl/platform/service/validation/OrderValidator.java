package com.awesomepizzasrl.platform.service.validation;

import com.awesomepizzasrl.platform.exception.OrderException;
import com.awesomepizzasrl.platform.service.dto.CreateOrderDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OrderValidator {

    public void validate(CreateOrderDto order, Set<String> variants) throws OrderException {
        if(variants == null || variants.isEmpty())
            throw new OrderException("Pizza variants not found");

        if (order == null)
            throw new OrderException("Order cannot be null");

        if (order.getIdOrder() == null)
            throw new OrderException("Order ID cannot be null");

        if (order.getUsername()== null || order.getUsername().isBlank())
            throw new OrderException("Username cannot be null or blank");

        if (order.getPizzas() == null || order.getPizzas().isEmpty())
            throw new OrderException("At least one pizza must be included in the order");

        order.getPizzas().forEach(p ->{
            if(p.getQuantity() <= 0 )
                throw new OrderException("Pizza quantity not valid");
            if(p.getType() == null || !variants.contains(p.getType()))
                throw new OrderException("Pizza variant not valid");
        });
    }
}
