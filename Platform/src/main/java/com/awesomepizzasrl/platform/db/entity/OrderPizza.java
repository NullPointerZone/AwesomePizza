package com.awesomepizzasrl.platform.db.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "order_pizza", schema = "public")
@Getter
@Setter
@AllArgsConstructor
public class OrderPizza {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idOrder", nullable = false)
    private Order order;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "pizza_variant", nullable = false)
    private String pizzaVariant;
}