package com.awesomepizzasrl.client.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/pizza")
public class PizzaController {

    //TODO
//    @GetMapping("/list")
//    public ResponseEntity<String> getPizzaList() {
//        String status = orders.get(orderId);
//        if (status == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(status);
//    }

}
