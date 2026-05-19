package com.flowers.inventoryservice.infrastructure;

import com.flowers.inventoryservice.domain.FlowerShop;
import com.flowers.inventoryservice.domain.daocontracts.IFlowerShopDAO;
import com.flowers.inventoryservice.infrastructure.repositories.FlowerShopJpaRepository;
import com.flowers.inventoryservice.infrastructure.tableentities.FlowerShopEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FlowerShopDAO implements IFlowerShopDAO {

    private final FlowerShopJpaRepository flowerShopJpaRepository;

    public FlowerShopDAO(FlowerShopJpaRepository flowerShopJpaRepository) {
        this.flowerShopJpaRepository = flowerShopJpaRepository;
    }

    @Override
    public List<FlowerShop> flowerShopList() {
        return flowerShopJpaRepository.findAll()
                .stream()
                .map(FlowerShopEntity::toFlowerShop)
                .collect(Collectors.toList());
    }

    @Override
    public FlowerShop flowerShopById(int id) {
        Optional<FlowerShopEntity> entity = flowerShopJpaRepository.findById(id);
        return entity.map(FlowerShopEntity::toFlowerShop).orElse(null);
    }

    @Override
    public boolean insert(FlowerShop flowerShop) {
        try {
            FlowerShopEntity entity = new FlowerShopEntity(flowerShop);
            flowerShopJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(FlowerShop flowerShop) {
        try {
            if (flowerShop.getId() == null || !flowerShopJpaRepository.existsById(flowerShop.getId().getFlowerShopId())) {
                return false;
            }
            FlowerShopEntity entity = new FlowerShopEntity(flowerShop);
            flowerShopJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            if (!flowerShopJpaRepository.existsById(id)) {
                return false;
            }
            flowerShopJpaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
