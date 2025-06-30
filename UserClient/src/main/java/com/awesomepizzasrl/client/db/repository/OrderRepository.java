package com.awesomepizzasrl.client.db.repository;

import com.awesomepizzasrl.platform.db.entity.OrderEntity;
import com.awesomepizzasrl.platform.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    @Transactional
    @Modifying
    @Query("update OrderEntity o set o.status = ?1 where o.idOrder = ?2")
    int updateStatusByIdOrder(OrderStatus status, UUID idOrder);
}