package com.flowers.statisticsservice.infrastructure.repositories;

import com.flowers.statisticsservice.infrastructure.tableentities.SaleStatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleStatisticJpaRepository extends JpaRepository<SaleStatisticEntity, Integer> {
}
