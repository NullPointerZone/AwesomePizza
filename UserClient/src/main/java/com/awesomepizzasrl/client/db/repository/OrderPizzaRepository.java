package com.awesomepizzasrl.client.db.repository;

import com.awesomepizzasrl.platform.db.entity.OrderPizzaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderPizzaRepository extends JpaRepository<OrderPizzaEntity, UUID> {

}