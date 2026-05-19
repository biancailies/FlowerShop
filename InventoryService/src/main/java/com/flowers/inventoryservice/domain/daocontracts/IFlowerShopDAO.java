package com.flowers.inventoryservice.domain.daocontracts;

import com.flowers.inventoryservice.domain.FlowerShop;

import java.util.List;

public interface IFlowerShopDAO {

    List<FlowerShop> flowerShopList();

    FlowerShop flowerShopById(int id);

    boolean insert(FlowerShop flowerShop);

    boolean update(FlowerShop flowerShop);

    boolean delete(int id);
}
