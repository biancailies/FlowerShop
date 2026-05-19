package com.flowers.flowercatalogservice.infrastructure.repositories;

import com.flowers.flowercatalogservice.infrastructure.tableentities.FlowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerJpaRepository extends JpaRepository<FlowerEntity, Integer> {

    List<FlowerEntity> findByNameContainingIgnoreCase(String name);
}
