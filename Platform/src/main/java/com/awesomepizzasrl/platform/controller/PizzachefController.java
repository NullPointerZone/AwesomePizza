package com.awesomepizzasrl.platform.controller;

import com.awesomepizzasrl.platform.service.PizzachefService;
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
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PizzachefController implements MqttCallback {

    private final PizzachefService pizzachefService;

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            UUID orderId = UUID.fromString(new String(message.getPayload(), StandardCharsets.UTF_8));
            log.info("Received order ID: {} from topic {}", orderId, topic);
            pizzachefService.processOrder(orderId);
        } catch (Exception e) {
            log.error("Invalid UUID payload: {}", new String(message.getPayload(), StandardCharsets.UTF_8), e);
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
