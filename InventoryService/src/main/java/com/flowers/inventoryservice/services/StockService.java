package com.flowers.inventoryservice.services;

import com.flowers.inventoryservice.infrastructure.clients.FlowerCatalogClient;
import com.flowers.inventoryservice.domain.FlowerShop;
import com.flowers.inventoryservice.domain.Stock;
import com.flowers.inventoryservice.domain.daocontracts.IFlowerShopDAO;
import com.flowers.inventoryservice.domain.daocontracts.IStockDAO;
import com.flowers.inventoryservice.services.events.SaleCompletedEvent;
import com.flowers.inventoryservice.services.dto.ExpandedStockDTO;
import com.flowers.inventoryservice.services.dto.FlowerDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    private final IStockDAO stockDAO;
    private final IFlowerShopDAO flowerShopDAO;
    private final FlowerCatalogClient flowerCatalogClient;
    private final ApplicationEventPublisher eventPublisher;

    public StockService(IStockDAO stockDAO,
                        IFlowerShopDAO flowerShopDAO,
                        FlowerCatalogClient flowerCatalogClient,
                        ApplicationEventPublisher eventPublisher) {
        this.stockDAO = stockDAO;
        this.flowerShopDAO = flowerShopDAO;
        this.flowerCatalogClient = flowerCatalogClient;
        this.eventPublisher = eventPublisher;
    }

    public List<Stock> getAllStocks() {
        return stockDAO.stockList();
    }

    public Stock getStockById(int id) {
        return stockDAO.stockById(id);
    }

    public List<Stock> getStocksByFlowerShopId(int flowerShopId) {
        return stockDAO.stocksByFlowerShopId(flowerShopId);
    }

    public List<Stock> getStocksByFlowerId(int flowerId) {
        return stockDAO.stocksByFlowerId(flowerId);
    }

    public List<Stock> filterStocks(Integer flowerShopId, String color, Integer minQuantity) {
        return stockDAO.filterStocks(flowerShopId, color, minQuantity);
    }

    public boolean createStock(Stock stock) {
        return stockDAO.insert(stock);
    }

    public boolean updateStock(Stock stock) {
        return stockDAO.update(stock);
    }

    public boolean deleteStock(int id) {
        return stockDAO.delete(id);
    }

    public boolean sellFlower(int stockId, int quantity) {
        Stock stock = stockDAO.stockById(stockId);
        if (stock == null) return false;
        if (stock.getQuantity() < quantity) return false;
        
        boolean success = stockDAO.sellFlower(stockId, quantity);
        if (success) {
            eventPublisher.publishEvent(new SaleCompletedEvent(
                    stockId,
                    stock.getFlowerId(),
                    stock.getFlowerShopId().getFlowerShopId(),
                    quantity
            ));
        }
        return success;
    }

    public FlowerDTO getFlowerDetails(int flowerId) {
        return flowerCatalogClient.getFlowerById(flowerId);
    }

    public String getFlowerImageUrl(int flowerId) {
        return flowerCatalogClient.getFlowerImageUrl(flowerId);
    }

    public List<ExpandedStockDTO> getExpandedStocks() {
        List<Stock> stocks = stockDAO.stockList();
        List<ExpandedStockDTO> expandedList = new ArrayList<>();

        for (Stock stock : stocks) {
            FlowerShop flowerShop = flowerShopDAO.flowerShopById(stock.getFlowerShopId().getFlowerShopId());
            String flowerShopName = (flowerShop != null) ? flowerShop.getName() : "Unknown";

            FlowerDTO flowerDTO = getFlowerDetails(stock.getFlowerId());
            String flowerName = (flowerDTO != null) ? flowerDTO.getName() : "Unknown";
            float sellingPrice = (flowerDTO != null) ? flowerDTO.getSellingPrice() : 0;
            
            String imageUrl = getFlowerImageUrl(stock.getFlowerId());

            ExpandedStockDTO expanded = new ExpandedStockDTO(
                    stock.getId().getStockId(),
                    stock.getFlowerShopId().getFlowerShopId(),
                    flowerShopName,
                    stock.getFlowerId(),
                    flowerName,
                    sellingPrice,
                    stock.getColor(),
                    stock.getQuantity(),
                    imageUrl
            );
            expandedList.add(expanded);
        }

        return expandedList;
    }
}
