package com.awesomepizzasrl.client.db.entity;

import awesomepizzasrl.eventmodel.model.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "order", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderPizzaEntity> pizzas;
}