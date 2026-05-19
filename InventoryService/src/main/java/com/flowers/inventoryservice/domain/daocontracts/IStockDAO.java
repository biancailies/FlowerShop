package com.flowers.inventoryservice.domain.daocontracts;

import com.flowers.inventoryservice.domain.Stock;

import java.util.List;

public interface IStockDAO {

    List<Stock> stockList();

    Stock stockById(int id);

    List<Stock> stocksByFlowerShopId(int flowerShopId);

    List<Stock> stocksByFlowerId(int flowerId);

    List<Stock> filterStocks(Integer flowerShopId, String color, Integer minQuantity);

    boolean insert(Stock stock);

    boolean update(Stock stock);

    boolean delete(int id);

    boolean sellFlower(int stockId, int quantity);
}
