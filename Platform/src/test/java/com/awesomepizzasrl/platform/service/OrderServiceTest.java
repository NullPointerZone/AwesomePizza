package com.awesomepizzasrl.platform.service;

import com.awesomepizzasrl.platform.configuration.MqttProperties;
import com.awesomepizzasrl.platform.db.entity.OrderEntity;
import com.awesomepizzasrl.platform.db.entity.PizzaVariantEntity;
import com.awesomepizzasrl.platform.db.mapper.OrderEntityMapper;
import com.awesomepizzasrl.platform.db.repository.OrderRepository;
import com.awesomepizzasrl.platform.db.repository.PizzaVariantRepository;
import com.awesomepizzasrl.platform.service.dto.CreateOrderDto;
import com.awesomepizzasrl.platform.service.dto.PizzaDto;
import com.awesomepizzasrl.platform.service.validation.OrderValidator;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    PizzaVariantRepository pizzaVariantRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderValidator orderValidator;
    @Mock
    OrderEntityMapper orderEntityMapper;
    @Mock
    MqttProperties mqttProperties;
    @Mock
    OrderStateManager orderStateManager;
    @Mock
    MqttAsyncClient mqttAsyncClient;
    @InjectMocks
    OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void processOrder_whenValid_shouldSaveAndPublish() throws Exception {
        UUID orderId = UUID.randomUUID();
        CreateOrderDto orderDto = new CreateOrderDto(orderId, "mario", List.of(new PizzaDto("Margherita", 1)), null);

        when(pizzaVariantRepository.findAll()).thenReturn(List.of(new PizzaVariantEntity("Margherita")));
        when(orderEntityMapper.map(orderDto)).thenReturn(new OrderEntity());
        when(mqttProperties.getPizzachefTopic()).thenReturn("pizza/orders");

        orderService.processOrder(orderDto);

        verify(orderValidator).validate(eq(orderDto), eq(Set.of("Margherita")));
        verify(orderRepository).save(any(OrderEntity.class));
        verify(mqttAsyncClient).publish(eq("pizza/orders"), eq(orderDto.getIdOrder().toString().getBytes(StandardCharsets.UTF_8)), eq(1), eq(false));
        verify(orderStateManager, never()).setOrderDeclined(any());
    }

    @Test
    void processOrder_whenValidationFails_shouldSetDeclinedAndRethrow() throws MqttException {
        UUID orderId = UUID.randomUUID();
        CreateOrderDto orderDto = new CreateOrderDto(orderId, "mario", List.of(new PizzaDto("Margherita", 1)), null);

        when(pizzaVariantRepository.findAll()).thenReturn(List.of(new PizzaVariantEntity("Margherita")));
        doThrow(new RuntimeException("validation failed"))
                .when(orderValidator).validate(any(), any());

        assertThrows(RuntimeException.class, () -> orderService.processOrder(orderDto));

        verify(orderValidator).validate(eq(orderDto), eq(Set.of("Margherita")));
        verify(orderStateManager).setOrderDeclined(orderDto.getIdOrder());
        verify(orderRepository, never()).save(any());
        verify(mqttAsyncClient, never()).publish(any(), any(), anyInt(), anyBoolean());
    }

    @Test
    void processOrder_whenMqttFails_shouldSetDeclinedAndRethrow() throws Exception {
        UUID orderId = UUID.randomUUID();
        CreateOrderDto orderDto = new CreateOrderDto(orderId, "mario", List.of(new PizzaDto("Margherita", 1)), null);

        when(pizzaVariantRepository.findAll()).thenReturn(List.of(new PizzaVariantEntity("Margherita")));
        when(orderEntityMapper.map(orderDto)).thenReturn(new OrderEntity());
        when(mqttProperties.getPizzachefTopic()).thenReturn("pizza/orders");

        doThrow(new MqttException(1)).when(mqttAsyncClient)
                .publish(any(), any(), anyInt(), anyBoolean());

        assertThrows(MqttException.class, () -> orderService.processOrder(orderDto));

        verify(orderRepository).save(any(OrderEntity.class));
        verify(orderStateManager).setOrderDeclined(orderDto.getIdOrder());
    }
}