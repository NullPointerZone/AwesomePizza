package com.awesomepizzasrl.client.db.repository;

import com.awesomepizzasrl.platform.db.entity.PizzaVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaVariantRepository  extends JpaRepository<PizzaVariantEntity, String> {
}
