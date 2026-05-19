package com.flowers.flowercatalogservice.infrastructure.repositories;

import com.flowers.flowercatalogservice.infrastructure.tableentities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageJpaRepository extends JpaRepository<ImageEntity, Integer> {

    List<ImageEntity> findByFlowerId(int flowerId);
}
