package com.awesomepizzasrl.platform.configuration;

import com.awesomepizzasrl.platform.controller.OrderController;
import com.awesomepizzasrl.platform.controller.PizzachefController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MqttSubscriberConfig {

    private final MqttProperties properties;
    private final OrderController orderController;
    private final PizzachefController pizzachefController;

    @Bean(name = "subscriberConnectionOptions")
    public MqttConnectionOptions subscriberConnectionOptions() {
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setCleanStart(false);
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(30);
        return options;
    }

    @Bean(name = "mqttSubscriberOrder")
    public MqttAsyncClient mqttSubscriberOrder(@Qualifier("subscriberConnectionOptions")MqttConnectionOptions subscriberConnectionOptions) throws MqttException {
        String clientId = properties.getClientId() + "-subOrder-" + UUID.randomUUID();
        MqttAsyncClient client = new MqttAsyncClient(properties.getBrokerUrl(), clientId);

        log.info("Connecting MQTT Subscriber: {}", clientId);

        client.connect(subscriberConnectionOptions).waitForCompletion();
        log.info("Connected MQTT Subscriber: {}", clientId);

        client.setCallback(orderController);

        String topic = properties.getOrderTopic();
        client.subscribe(topic, 1);
        log.info("Subscribed to topic: {}", topic);

        return client;
    }

    @Bean(name = "mqttSubscriberPizzachef")
    public MqttAsyncClient mqttSubscriberPizzachef(@Qualifier("subscriberConnectionOptions")MqttConnectionOptions subscriberConnectionOptions) throws MqttException {
        String clientId = properties.getClientId() + "-subPizzachef-" + UUID.randomUUID();
        MqttAsyncClient client = new MqttAsyncClient(properties.getBrokerUrl(), clientId);

        log.info("Connecting MQTT Subscriber: {}", clientId);

        client.connect(subscriberConnectionOptions).waitForCompletion();
        log.info("Connected MQTT Subscriber: {}", clientId);

        client.setCallback(pizzachefController);

        String topic = properties.getPizzachefTopic();
        client.subscribe(topic, 1);
        log.info("Subscribed to topic: {}", topic);

        return client;
    }
}
