package com.awesomepizzasrl.platform.controller;

import com.awesomepizzasrl.platform.service.PizzachefService;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class PizzachefControllerTest {
    @Mock
    PizzachefService pizzachefService;
    @InjectMocks
    PizzachefController pizzachefController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void messageArrived_whenValidUUID_callsProcessOrder() {
        UUID orderId = UUID.randomUUID();
        String topic = "test/topic";
        MqttMessage message = new MqttMessage(orderId.toString().getBytes(StandardCharsets.UTF_8));

        assertDoesNotThrow(() -> pizzachefController.messageArrived(topic, message));

        verify(pizzachefService, times(1)).processOrder(orderId);
    }

    @Test
    void messageArrived_whenInvalidUUID_doesNotThrowException() {
        String invalidPayload = "not-a-uuid";
        String topic = "test/topic";
        MqttMessage message = new MqttMessage(invalidPayload.getBytes(StandardCharsets.UTF_8));

        assertDoesNotThrow(() -> pizzachefController.messageArrived(topic, message));
    }
}