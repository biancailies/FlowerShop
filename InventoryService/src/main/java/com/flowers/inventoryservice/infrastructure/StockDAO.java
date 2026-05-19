package com.flowers.inventoryservice.infrastructure;

import com.flowers.inventoryservice.domain.Stock;
import com.flowers.inventoryservice.domain.daocontracts.IStockDAO;
import com.flowers.inventoryservice.infrastructure.repositories.StockJpaRepository;
import com.flowers.inventoryservice.infrastructure.tableentities.StockEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StockDAO implements IStockDAO {

    private final StockJpaRepository stockJpaRepository;

    public StockDAO(StockJpaRepository stockJpaRepository) {
        this.stockJpaRepository = stockJpaRepository;
    }

    @Override
    public List<Stock> stockList() {
        return stockJpaRepository.findAll()
                .stream()
                .map(StockEntity::toStock)
                .collect(Collectors.toList());
    }

    @Override
    public Stock stockById(int id) {
        Optional<StockEntity> entity = stockJpaRepository.findById(id);
        return entity.map(StockEntity::toStock).orElse(null);
    }

    @Override
    public List<Stock> stocksByFlowerShopId(int flowerShopId) {
        return stockJpaRepository.findByFlowerShopId(flowerShopId)
                .stream()
                .map(StockEntity::toStock)
                .collect(Collectors.toList());
    }

    @Override
    public List<Stock> stocksByFlowerId(int flowerId) {
        return stockJpaRepository.findByFlowerId(flowerId)
                .stream()
                .map(StockEntity::toStock)
                .collect(Collectors.toList());
    }

    @Override
    public List<Stock> filterStocks(Integer flowerShopId, String color, Integer minQuantity) {
        List<StockEntity> all = stockJpaRepository.findAll();

        return all.stream()
                .filter(s -> flowerShopId == null || s.getFlowerShopId() == flowerShopId)
                .filter(s -> color == null || color.isEmpty() || s.getColor().toLowerCase().contains(color.toLowerCase()))
                .filter(s -> minQuantity == null || s.getQuantity() >= minQuantity)
                .map(StockEntity::toStock)
                .collect(Collectors.toList());
    }

    @Override
    public boolean insert(Stock stock) {
        try {
            StockEntity entity = new StockEntity(stock);
            stockJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Stock stock) {
        try {
            if (stock.getId() == null || !stockJpaRepository.existsById(stock.getId().getStockId())) {
                return false;
            }
            StockEntity entity = new StockEntity(stock);
            stockJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            if (!stockJpaRepository.existsById(id)) {
                return false;
            }
            stockJpaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sellFlower(int stockId, int quantity) {
        try {
            Optional<StockEntity> optional = stockJpaRepository.findById(stockId);
            if (optional.isEmpty()) {
                return false;
            }
            StockEntity entity = optional.get();
            if (entity.getQuantity() < quantity) {
                return false;
            }
            entity.setQuantity(entity.getQuantity() - quantity);
            stockJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
