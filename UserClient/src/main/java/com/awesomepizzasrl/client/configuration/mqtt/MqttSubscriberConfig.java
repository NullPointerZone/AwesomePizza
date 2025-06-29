package com.awesomepizzasrl.client.configuration.mqtt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MqttSubscriberConfig {

    private final MqttProperties properties;

    @Bean
    public MqttConnectionOptions subscriberConnectionOptions() {
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setCleanStart(false);
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(30);
        return options;
    }

    @Bean
    public MqttAsyncClient mqttSubscriber(MqttConnectionOptions subscriberConnectionOptions) throws MqttException {
        //TODO
//        String dynamicClientId = properties.getClientId() + "-sub-" + UUID.randomUUID();

        MqttAsyncClient client = new MqttAsyncClient(
                properties.getBrokerUrl(),
              ""
              //  dynamicClientId
        );

//        log.info("Connecting MQTT Subscriber: {}", dynamicClientId);
//        client.connect(subscriberConnectionOptions).waitForCompletion();
//        log.info("Connected MQTT Subscriber: {}", dynamicClientId);
//
//        // subscribe to "myorder-<UUID>"
//        String topic = properties.getSubscriberTopicPrefix() + "-" + UUID.randomUUID();
//        log.info("Subscribing to topic: {}", topic);
//        client.subscribe(topic, 1); // QoS 1

        return client;
    }
}
