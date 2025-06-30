package com.awesomepizzasrl.platform.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MqttPublisherConfig {

    private final MqttProperties properties;

    @Bean(name = "publisherConnectionOptions")
    public MqttConnectionOptions publisherConnectionOptions() {
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setCleanStart(true);
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(30);
        options.setUserName(properties.getUsername());
        options.setPassword(properties.getPassword().getBytes(StandardCharsets.UTF_8));
        return options;
    }

    @Bean(name = "mqttPublisherClient")
    public MqttAsyncClient mqttPublisher(@Qualifier("publisherConnectionOptions")MqttConnectionOptions publisherConnectionOptions) throws MqttException {
        String dynamicClientId = properties.getClientId() + "-pub-" + UUID.randomUUID();

        MqttAsyncClient client = new MqttAsyncClient(
                properties.getBrokerUrl(),
                dynamicClientId
        );

        log.info("Connecting MQTT Publisher: {}", dynamicClientId);
        client.connect(publisherConnectionOptions).waitForCompletion();
        log.info("Connected MQTT Publisher: {}", dynamicClientId);

        return client;
    }

}