package com.flowers.inventoryservice.services;

import com.flowers.inventoryservice.domain.FlowerShop;
import com.flowers.inventoryservice.domain.daocontracts.IFlowerShopDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowerShopService {

    private final IFlowerShopDAO flowerShopDAO;

    public FlowerShopService(IFlowerShopDAO flowerShopDAO) {
        this.flowerShopDAO = flowerShopDAO;
    }

    public List<FlowerShop> getAllFlowerShops() {
        return flowerShopDAO.flowerShopList();
    }

    public FlowerShop getFlowerShopById(int id) {
        return flowerShopDAO.flowerShopById(id);
    }

    public boolean createFlowerShop(FlowerShop flowerShop) {
        return flowerShopDAO.insert(flowerShop);
    }

    public boolean updateFlowerShop(FlowerShop flowerShop) {
        return flowerShopDAO.update(flowerShop);
    }

    public boolean deleteFlowerShop(int id) {
        return flowerShopDAO.delete(id);
    }
}
