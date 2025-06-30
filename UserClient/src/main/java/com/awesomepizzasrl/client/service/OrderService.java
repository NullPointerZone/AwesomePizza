package com.awesomepizzasrl.client.service;

import com.awesomepizzasrl.client.configuration.mqtt.MqttProperties;
import com.awesomepizzasrl.client.service.mapper.OrderMapper;
import com.awesomepizzasrl.client.model.CreateOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderMapper mapper;
    private final MqttAsyncClient mqttPublisher;
    private final MqttProperties mqttProperties;

    @Autowired
    public OrderService(OrderMapper mapper, @Qualifier("mqttPublisherClient") MqttAsyncClient mqttPublisher, MqttProperties mqttProperties) {
        this.mapper = mapper;
        this.mqttPublisher = mqttPublisher;
        this.mqttProperties = mqttProperties;
    }

    public UUID createOrder(CreateOrder request) throws JsonProcessingException, MqttException {
        request.validate();
        awesomepizzasrl.eventmodel.model.CreateOrder createOrder = mapper.toCreateOrder(request);

        String payload = new ObjectMapper().writeValueAsString(createOrder);
        MqttMessage message = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
        message.setQos(mqttProperties.getPublishQos());
        mqttPublisher.publish(mqttProperties.getOrderTopic(), message);

        return createOrder.getIdOrder();
    }

}
