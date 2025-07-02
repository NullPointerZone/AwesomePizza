package com.awesomepizzasrl.client.service;

import com.awesomepizzasrl.client.configuration.mqtt.MqttProperties;
import com.awesomepizzasrl.client.db.entity.OrderEntity;
import com.awesomepizzasrl.client.db.repository.OrderRepository;
import com.awesomepizzasrl.client.exception.RequestException;
import com.awesomepizzasrl.client.model.CreateOrder;
import com.awesomepizzasrl.client.service.dto.OrderDto;
import com.awesomepizzasrl.client.service.mapper.OrderDtoMapper;
import com.awesomepizzasrl.client.service.mapper.OrderMapper;
import com.awesomepizzasrl.client.service.validation.CreateOrderValidator;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    OrderMapper mapper;
    @Mock
    MqttAsyncClient mqttPublisher;
    @Mock
    MqttProperties mqttProperties;
    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderDtoMapper orderDtoMapper;
    @Mock
    CreateOrderValidator createOrderValidator;
    @InjectMocks
    OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_whenValidRequest_thenPublishesMqttMessageAndReturnsOrderId() throws Exception {
        CreateOrder request = new CreateOrder();

        awesomepizzasrl.eventmodel.model.CreateOrder mappedOrder = new awesomepizzasrl.eventmodel.model.CreateOrder();
        UUID orderId = UUID.randomUUID();
        mappedOrder.setIdOrder(orderId);

        when(mapper.toCreateOrder(request)).thenReturn(mappedOrder);
        when(mqttProperties.getPublishQos()).thenReturn(1);
        when(mqttProperties.getOrderTopic()).thenReturn("order");

        doNothing().when(createOrderValidator).validate(request);

        ArgumentCaptor<MqttMessage> messageCaptor = ArgumentCaptor.forClass(MqttMessage.class);

        assertDoesNotThrow(() -> orderService.createOrder(request));

        verify(createOrderValidator).validate(request);
        verify(mqttPublisher).publish(eq("order"), messageCaptor.capture());
        MqttMessage publishedMessage = messageCaptor.getValue();

        String expectedJson = "{\"idOrder\":\""+ orderId +"\",\"username\":null,\"pizzas\":null}";

        assertEquals(expectedJson, new String(publishedMessage.getPayload(), StandardCharsets.UTF_8));
    }

    @Test
    void createOrder_whenValidatorThrows_thenExceptionPropagated(){
        CreateOrder request = new CreateOrder();

        doThrow(new RequestException(HttpStatus.BAD_REQUEST, "Invalid order")).when(createOrderValidator).validate(request);

        assertThrows(RequestException.class, () -> orderService.createOrder(request));
    }

    @Test
    void getOrders_whenOrdersFound_thenReturnsMappedList() {
        String username = "user123";
        OrderEntity entity = new OrderEntity();
        List<OrderEntity> entities = List.of(entity);
        OrderDto dto = new OrderDto();

        when(orderRepository.findByUsername(username)).thenReturn(entities);
        when(orderDtoMapper.map(entity)).thenReturn(dto);

        List<OrderDto> result = orderService.getOrders(username);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void getOrders_whenNoOrders_thenThrowsNotFound() {
        String username = "user123";
        when(orderRepository.findByUsername(username)).thenReturn(Collections.emptyList());

        RequestException ex = assertThrows(RequestException.class, () -> orderService.getOrders(username));
        assertEquals(HttpStatus.NOT_FOUND, ex.getCode());
        assertEquals("Orders not found", ex.getBody());
    }

    @Test
    void getOrder_whenOrderExists_thenReturnsMappedDto() {
        UUID orderId = UUID.randomUUID();
        OrderEntity entity = new OrderEntity();
        OrderDto dto = new OrderDto();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));
        when(orderDtoMapper.map(entity)).thenReturn(dto);

        OrderDto result = orderService.getOrder(orderId);

        assertEquals(dto, result);
    }

    @Test
    void getOrder_whenOrderNotFound_thenThrowsNotFound() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        RequestException ex = assertThrows(RequestException.class, () -> orderService.getOrder(orderId));
        assertEquals(HttpStatus.NOT_FOUND, ex.getCode());
        assertEquals("Order not found", ex.getBody());
    }
}