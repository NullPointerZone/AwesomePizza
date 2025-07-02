package com.awesomepizzasrl.platform.controller;


import awesomepizzasrl.eventmodel.model.CreateOrder;
import awesomepizzasrl.eventmodel.model.OrderStatus;
import com.awesomepizzasrl.platform.service.OrderService;
import com.awesomepizzasrl.platform.service.dto.CreateOrderDto;
import com.awesomepizzasrl.platform.service.mapper.CreateOrderDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    OrderService orderService;
    @Mock
    CreateOrderDtoMapper createOrderDtoMapper;
    @Mock
    ObjectMapper objectMapper;
    @InjectMocks
    OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void messageArrived_whenValidJson_processesOrder() throws Exception {
        String topic = "some/topic";
        String jsonPayload = "{\"username\":\"user1\",\"pizzas\":[]}";
        MqttMessage message = new MqttMessage(jsonPayload.getBytes(StandardCharsets.UTF_8));

        CreateOrder createOrder = new CreateOrder();
        CreateOrderDto dto = new CreateOrderDto(UUID.randomUUID(), "", new ArrayList<>(), OrderStatus.PENDING);

        when(objectMapper.readValue(jsonPayload, CreateOrder.class)).thenReturn(createOrder);
        when(createOrderDtoMapper.map(createOrder, OrderStatus.PENDING)).thenReturn(dto);

        assertDoesNotThrow(() -> orderController.messageArrived(topic, message));
    }

    @Test
    void messageArrived_whenInvalidJson_logsErrorAndDoesNotThrow() throws Exception {
        String topic = "some/topic";
        String badJson = "invalid json";
        MqttMessage message = new MqttMessage(badJson.getBytes(StandardCharsets.UTF_8));

        when(objectMapper.readValue(any(String.class), eq(CreateOrder.class))).thenThrow(new RuntimeException("parse error"));

        assertDoesNotThrow(() -> orderController.messageArrived(topic, message));

        verifyNoInteractions(orderService);
    }
}

