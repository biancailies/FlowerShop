package com.flowers.flowercatalogservice.infrastructure;

import com.flowers.flowercatalogservice.domain.Flower;
import com.flowers.flowercatalogservice.domain.daocontracts.IFlowerDAO;
import com.flowers.flowercatalogservice.infrastructure.repositories.FlowerJpaRepository;
import com.flowers.flowercatalogservice.infrastructure.tableentities.FlowerEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FlowerDAO implements IFlowerDAO {

    private final FlowerJpaRepository flowerJpaRepository;

    public FlowerDAO(FlowerJpaRepository flowerJpaRepository) {
        this.flowerJpaRepository = flowerJpaRepository;
    }

    @Override
    public List<Flower> flowerList() {
        return flowerJpaRepository.findAll()
                .stream()
                .map(FlowerEntity::toFlower)
                .collect(Collectors.toList());
    }

    @Override
    public List<Flower> flowerFilterByName(String search) {
        return flowerJpaRepository.findByNameContainingIgnoreCase(search)
                .stream()
                .map(FlowerEntity::toFlower)
                .collect(Collectors.toList());
    }

    @Override
    public Flower flowerById(int id) {
        Optional<FlowerEntity> entity = flowerJpaRepository.findById(id);
        return entity.map(FlowerEntity::toFlower).orElse(null);
    }

    @Override
    public boolean insert(Flower flower) {
        try {
            FlowerEntity entity = new FlowerEntity(flower);
            flowerJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Flower flower) {
        try {
            if (flower.getId() == null || !flowerJpaRepository.existsById(flower.getId().getFlowerId())) {
                return false;
            }
            FlowerEntity entity = new FlowerEntity(flower);
            flowerJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            if (!flowerJpaRepository.existsById(id)) {
                return false;
            }
            flowerJpaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
