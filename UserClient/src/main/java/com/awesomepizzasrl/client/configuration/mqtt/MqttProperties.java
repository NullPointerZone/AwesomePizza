package com.awesomepizzasrl.client.configuration.mqtt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "mqtt")
@Component
@Getter
@Setter
public class MqttProperties {

    private String brokerUrl;
    private String clientId;
    private String orderTopic;
    private int publishQos;
}
