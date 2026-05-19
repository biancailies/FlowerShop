package com.flowers.inventoryservice.infrastructure.repositories;

import com.flowers.inventoryservice.infrastructure.tableentities.FlowerShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerShopJpaRepository extends JpaRepository<FlowerShopEntity, Integer> {
}
