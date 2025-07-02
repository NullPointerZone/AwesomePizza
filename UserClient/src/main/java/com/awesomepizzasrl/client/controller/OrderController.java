package com.awesomepizzasrl.client.controller;

import com.awesomepizzasrl.client.exception.RequestException;
import com.awesomepizzasrl.client.model.CreateOrder;
import com.awesomepizzasrl.client.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrder order) {
        try {
            log.info("{}", order);
            return ResponseEntity
                    .ok()
                    .body(orderService.createOrder(order));
        }catch (RequestException ex){
            log.warn("Exception occurred for order: {}", order, ex);
            return ResponseEntity
                    .status(ex.getCode())
                    .body(ex.getBody());
        }catch (Exception ex){
            log.error("Exception for order: {}", order, ex);
            return ResponseEntity
                    .status(500)
                    .body("Unexpected server error");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getOrders(@RequestParam  String username) {
        try {
            return ResponseEntity
                    .ok()
                    .body(orderService.getOrders(username));
        }catch (RequestException ex){
            log.warn("Exception occurred for getOrders: {}", username, ex);
            return ResponseEntity
                    .status(ex.getCode())
                    .body(ex.getBody());
        }catch (Exception ex){
            log.error("Exception for getOrders: {}", username, ex);
            return ResponseEntity
                    .status(500)
                    .body("Unexpected server error");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getOrderStatus(@RequestParam  UUID orderId) {
        try {
            return ResponseEntity
                    .ok()
                    .body(orderService.getOrder(orderId));
        }catch (RequestException ex){
            log.warn("Exception occurred for getOrder: {}", orderId, ex);
            return ResponseEntity
                    .status(ex.getCode())
                    .body(ex.getBody());
        }catch (Exception ex){
            log.error("Exception for getOrder: {}", orderId, ex);
            return ResponseEntity
                    .status(500)
                    .body("Unexpected server error");
        }
    }
}
