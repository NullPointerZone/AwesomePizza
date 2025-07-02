package com.awesomepizzasrl.client.controller;

import awesomepizzasrl.eventmodel.model.OrderStatus;
import com.awesomepizzasrl.client.exception.RequestException;
import com.awesomepizzasrl.client.model.CreateOrder;
import com.awesomepizzasrl.client.service.OrderService;
import com.awesomepizzasrl.client.service.dto.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_whenValidOrder_thenReturn200() throws Exception {
        CreateOrder order = new CreateOrder();
        UUID orderId = new UUID(0L, 0L);

        when(orderService.createOrder(any(CreateOrder.class))).thenReturn(orderId);

        ResponseEntity<?> result = orderController.createOrder(order);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(orderId, result.getBody());
    }

    @Test
    void createOrder_whenInvalidOrder_thenReturnError() throws Exception {
        CreateOrder order = new CreateOrder();
        RequestException ex = new RequestException(HttpStatus.BAD_REQUEST, "Invalid order data");

        when(orderService.createOrder(any(CreateOrder.class))).thenThrow(ex);

        ResponseEntity<?> result = orderController.createOrder(order);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid order data", result.getBody());
    }

    @Test
    void createOrder_whenUnexpectedException_thenReturn500() throws Exception {
        CreateOrder order = new CreateOrder();

        when(orderService.createOrder(any(CreateOrder.class))).thenThrow(new RuntimeException("Unexpected"));

        ResponseEntity<?> result = orderController.createOrder(order);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Unexpected server error", result.getBody());
    }

    @Test
    void getOrders_whenValidUsername_thenReturn200() throws Exception {
        String username = "user";
        List<OrderDto> orders = List.of(new OrderDto(UUID.randomUUID(), username, OrderStatus.PENDING), new OrderDto(UUID.randomUUID(), username, OrderStatus.PENDING));

        when(orderService.getOrders(username)).thenReturn(orders);

        ResponseEntity<?> result = orderController.getOrders(username);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(orders, result.getBody());
    }

    @Test
    void getOrders_whenRequestException_thenReturnError() {
        String username = "user";
        RequestException ex = new RequestException(HttpStatus.NOT_FOUND, "User not found");

        when(orderService.getOrders(username)).thenThrow(ex);

        ResponseEntity<?> result = orderController.getOrders(username);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("User not found", result.getBody());
    }

    @Test
    void getOrders_whenUnexpectedException_thenReturn500() {
        String username = "user";

        when(orderService.getOrders(username)).thenThrow(new RuntimeException("Unexpected"));

        ResponseEntity<?> result = orderController.getOrders(username);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Unexpected server error", result.getBody());
    }

    @Test
    void getOrderStatus_whenValidOrderId_thenReturn200(){
        UUID orderId = UUID.randomUUID();
        String username = "user";
        OrderDto orderDto = new OrderDto(UUID.randomUUID(), username, OrderStatus.PENDING);

        when(orderService.getOrder(orderId)).thenReturn(orderDto);

        ResponseEntity<?> result = orderController.getOrderStatus(orderId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(orderDto, result.getBody());
    }

    @Test
    void getOrderStatus_whenRequestException_thenReturnError(){
        UUID orderId = UUID.randomUUID();
        RequestException ex = new RequestException(HttpStatus.NOT_FOUND, "Order not found");

        when(orderService.getOrder(orderId)).thenThrow(ex);

        ResponseEntity<?> result = orderController.getOrderStatus(orderId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Order not found", result.getBody());
    }

    @Test
    void getOrderStatus_whenUnexpectedException_thenReturn500(){
        UUID orderId = UUID.randomUUID();

        when(orderService.getOrder(orderId)).thenThrow(new RuntimeException("Unexpected"));

        ResponseEntity<?> result = orderController.getOrderStatus(orderId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Unexpected server error", result.getBody());
    }
}
