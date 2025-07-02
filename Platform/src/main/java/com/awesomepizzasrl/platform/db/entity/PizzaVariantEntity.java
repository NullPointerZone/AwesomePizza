package com.awesomepizzasrl.platform.db.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "pizza_variant", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PizzaVariantEntity {
    @Id
    @Column(name = "pizza_type", nullable = false, unique = true)
    private String pizzaType;
}