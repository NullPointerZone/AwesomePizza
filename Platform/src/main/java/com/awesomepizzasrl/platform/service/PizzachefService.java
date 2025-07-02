package com.awesomepizzasrl.platform.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PizzachefService {

    private final OrderStateManager orderStateManager;
    private long preparationDelayMs = 60000L;

    public void processOrder(UUID orderId) {
        orderStateManager.setOrderInProgress(orderId);
        log.info("Order {} received: changing status to IN_PROGRESS", orderId);
        try {
            log.info("Preparing order {}...", orderId);
            Thread.sleep(preparationDelayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Interrupted during preparation delay for order {}", orderId);
            return;
        }
        log.info("Order {} completed: changing status to READY", orderId);
        orderStateManager.setOrderReady(orderId);
    }
}