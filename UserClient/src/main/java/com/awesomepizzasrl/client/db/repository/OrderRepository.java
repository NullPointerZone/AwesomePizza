package com.awesomepizzasrl.client.db.repository;


import com.awesomepizzasrl.client.db.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    @Query("select o from OrderEntity o where o.username = ?1")
    List<OrderEntity> findByUsername(String username);

}