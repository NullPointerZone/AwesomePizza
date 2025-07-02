package com.awesomepizzasrl.platform.controller;


import awesomepizzasrl.eventmodel.model.CreateOrder;
import com.awesomepizzasrl.platform.service.mapper.CreateOrderDtoMapper;
import com.awesomepizzasrl.platform.service.dto.CreateOrderDto;
import awesomepizzasrl.eventmodel.model.OrderStatus;
import com.awesomepizzasrl.platform.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.stereotype.Controller;

import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController implements MqttCallback {

    private final OrderService orderService;
    private final CreateOrderDtoMapper createOrderDtoMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            String payload = new String(message.getPayload());
            log.info("Received message on topic {}: {}", topic, payload);
            CreateOrder createOrder = objectMapper.readValue(payload, CreateOrder.class);
            CreateOrderDto dto = createOrderDtoMapper.map(createOrder, OrderStatus.PENDING);
            orderService.processOrder(dto);
        } catch (Exception e) {
            log.error("{}", new String(message.getPayload(), StandardCharsets.UTF_8), e);
        }
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("MQTT connection complete to {}. Reconnected: {}", serverURI, reconnect);
    }

    @Override
    public void disconnected(MqttDisconnectResponse disconnectResponse) {
        log.warn("MQTT disconnected: {}", disconnectResponse.getReasonString());
    }

    @Override
    public void mqttErrorOccurred(MqttException exception) {
        log.error("MQTT error occurred", exception);
    }

    @Override
    public void deliveryComplete(IMqttToken iMqttToken) {}

    @Override
    public void authPacketArrived(int reasonCode, MqttProperties properties) {}
}
