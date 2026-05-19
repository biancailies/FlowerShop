package com.flowers.statisticsservice.infrastructure.repositories;

import com.flowers.statisticsservice.infrastructure.tableentities.StatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsJpaRepository extends JpaRepository<StatisticsEntity, Integer> {

    List<StatisticsEntity> findByFlowerShopId(int flowerShopId);
}
