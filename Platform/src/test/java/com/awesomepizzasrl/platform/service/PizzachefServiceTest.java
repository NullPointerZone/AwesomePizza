package com.awesomepizzasrl.platform.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.mockito.Mockito.*;

class PizzachefServiceTest {
    @Mock
    OrderStateManager orderStateManager;
    @InjectMocks
    PizzachefService pizzachefService;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        MockitoAnnotations.openMocks(this);
        Field delayField = PizzachefService.class.getDeclaredField("preparationDelayMs");
        delayField.setAccessible(true);
        delayField.set(pizzachefService, 1000);
    }

    @Test
    void processOrder_shouldSetInProgressAndReady(){
        UUID orderId = UUID.randomUUID();

        pizzachefService.processOrder(orderId);

        verify(orderStateManager).setOrderInProgress(orderId);
        verify(orderStateManager).setOrderReady(orderId);
    }
}

