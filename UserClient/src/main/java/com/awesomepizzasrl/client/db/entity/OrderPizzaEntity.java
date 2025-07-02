package com.awesomepizzasrl.client.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "order_pizza", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPizzaEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "pizza_variant", nullable = false)
    private String pizzaVariant;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idOrder", nullable = false)
    private OrderEntity orderEntity;
}