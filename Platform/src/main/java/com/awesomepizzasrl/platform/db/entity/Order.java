package com.awesomepizzasrl.platform.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "order", schema = "public")
@Getter
@Setter
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "idOrder", nullable = false, unique = true)
    private UUID idOrder;

    @Column(name = "username", nullable = false)
    private String username;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderPizza> pizzas;
}