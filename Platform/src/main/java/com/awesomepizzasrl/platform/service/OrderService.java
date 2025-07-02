package com.awesomepizzasrl.platform.service;

import com.awesomepizzasrl.platform.configuration.MqttProperties;
import com.awesomepizzasrl.platform.db.entity.PizzaVariantEntity;
import com.awesomepizzasrl.platform.db.mapper.OrderEntityMapper;
import com.awesomepizzasrl.platform.db.repository.OrderRepository;
import com.awesomepizzasrl.platform.db.repository.PizzaVariantRepository;
import com.awesomepizzasrl.platform.service.dto.CreateOrderDto;
import com.awesomepizzasrl.platform.service.validation.OrderValidator;
import jakarta.transaction.Transactional;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {


    private final PizzaVariantRepository pizzaVariantRepository;
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderEntityMapper orderEntityMapper;
    private final MqttProperties mqttProperties;
    private final OrderStateManager orderStateManager;
    private final MqttAsyncClient mqttAsyncClient;

    public OrderService(PizzaVariantRepository pizzaVariantRepository, OrderRepository orderRepository, OrderValidator orderValidator, OrderEntityMapper orderEntityMapper, MqttProperties mqttProperties,OrderStateManager orderStateManager,
            @Qualifier("mqttPublisherClient") MqttAsyncClient mqttAsyncClient) {
        this.pizzaVariantRepository = pizzaVariantRepository;
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderEntityMapper = orderEntityMapper;
        this.mqttProperties = mqttProperties;
        this.orderStateManager = orderStateManager;
        this.mqttAsyncClient = mqttAsyncClient;
    }

    @Transactional
    public void processOrder(CreateOrderDto order) throws MqttException {
        try {
            Set<String> pizzaTypes = pizzaVariantRepository.findAll().stream()
                    .map(PizzaVariantEntity::getPizzaType)
                    .collect(Collectors.toSet());

            orderValidator.validate(order, pizzaTypes);
            orderRepository.save(orderEntityMapper.map(order));
            mqttAsyncClient.publish(mqttProperties.getPizzachefTopic(),  order.getIdOrder().toString().getBytes(StandardCharsets.UTF_8), 1, false);
        }catch (Exception ex){
            orderStateManager.setOrderDeclined(order.getIdOrder());
            throw ex;
        }
    }
}
