package com.awesomepizzasrl.platform.service;

import com.awesomepizzasrl.platform.db.repository.OrderRepository;
import com.awesomepizzasrl.platform.model.OrderStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class OrderStateManager {

    private final OrderRepository orderRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void setOrderPending(UUID orderId) {
        orderRepository.updateStatusByIdOrder(OrderStatus.PENDING, orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void setOrderInProgress(UUID orderId){
        orderRepository.updateStatusByIdOrder(OrderStatus.IN_PROGRESS, orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void setOrderReady(UUID orderId) {
        orderRepository.updateStatusByIdOrder(OrderStatus.READY, orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void setOrderDeclined(UUID orderId) {
        orderRepository.updateStatusByIdOrder(OrderStatus.DECLINED, orderId);
    }


}
