package com.awesomepizzasrl.client.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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