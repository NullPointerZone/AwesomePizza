package com.awesomepizzasrl.client.service;

import com.awesomepizzasrl.client.configuration.mqtt.MqttProperties;
import com.awesomepizzasrl.client.db.entity.OrderEntity;
import com.awesomepizzasrl.client.db.repository.OrderRepository;
import com.awesomepizzasrl.client.exception.RequestException;
import com.awesomepizzasrl.client.service.dto.OrderDto;
import com.awesomepizzasrl.client.service.mapper.OrderDtoMapper;
import com.awesomepizzasrl.client.service.mapper.OrderMapper;
import com.awesomepizzasrl.client.model.CreateOrder;
import com.awesomepizzasrl.client.service.validation.CreateOrderValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderMapper mapper;
    private final MqttAsyncClient mqttPublisher;
    private final MqttProperties mqttProperties;
    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderDtoMapper;
    private final CreateOrderValidator createOrderValidator;

    @Autowired
    public OrderService(OrderMapper mapper, @Qualifier("mqttPublisherClient") MqttAsyncClient mqttPublisher, MqttProperties mqttProperties, OrderRepository orderRepository, OrderDtoMapper orderDtoMapper, CreateOrderValidator createOrderValidator) {
        this.mapper = mapper;
        this.mqttPublisher = mqttPublisher;
        this.mqttProperties = mqttProperties;
        this.orderRepository = orderRepository;
        this.orderDtoMapper = orderDtoMapper;
        this.createOrderValidator = createOrderValidator;
    }

    public UUID createOrder(CreateOrder request) throws JsonProcessingException, MqttException {
        createOrderValidator.validate(request);

        awesomepizzasrl.eventmodel.model.CreateOrder createOrder = mapper.toCreateOrder(request);
        String payload = new ObjectMapper().writeValueAsString(createOrder);
        MqttMessage message = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
        message.setQos(mqttProperties.getPublishQos());
        mqttPublisher.publish(mqttProperties.getOrderTopic(), message);

        return createOrder.getIdOrder();
    }


    public List<OrderDto> getOrders(String username){
        List<OrderEntity> orders = orderRepository.findByUsername(username);

        if(orders.isEmpty())
            throw new RequestException(HttpStatus.NOT_FOUND, "Orders not found");
        return orders.stream()
                .map(orderDtoMapper::map)
                .toList();
    }

    public OrderDto getOrder(UUID orderId){
        Optional<OrderEntity> result = orderRepository.findById(orderId);

        if(result.isEmpty())
            throw new RequestException(HttpStatus.NOT_FOUND, "Order not found");
        return orderDtoMapper.map(result.get());
    }

}
