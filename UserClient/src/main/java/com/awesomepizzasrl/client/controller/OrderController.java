package com.awesomepizzasrl.client.controller;

import com.awesomepizzasrl.client.exception.RequestException;
import com.awesomepizzasrl.client.model.CreateOrder;
import com.awesomepizzasrl.client.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //TODO
//    @GetMapping("/list")
//    public ResponseEntity<String> getOrders(@RequestBody("user") Long orderId) {
//        String status = orders.get(orderId);
//        if (status == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(status);
//    }
//
//    @GetMapping("/status")
//    public ResponseEntity<String> getOrderStatus(@RequestBody("orderId") Long orderId) {
//        String status = orders.get(orderId);
//        if (status == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(status);
//    }
}
