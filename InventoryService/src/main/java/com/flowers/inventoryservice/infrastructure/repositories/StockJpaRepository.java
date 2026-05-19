package com.flowers.inventoryservice.infrastructure.repositories;

import com.flowers.inventoryservice.infrastructure.tableentities.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockJpaRepository extends JpaRepository<StockEntity, Integer> {

    List<StockEntity> findByFlowerShopId(int flowerShopId);

    List<StockEntity> findByFlowerId(int flowerId);

    List<StockEntity> findByColorContainingIgnoreCase(String color);
}
