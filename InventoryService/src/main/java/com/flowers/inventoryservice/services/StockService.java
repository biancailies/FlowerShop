package com.flowers.inventoryservice.services;

import com.flowers.inventoryservice.domain.FlowerShop;
import com.flowers.inventoryservice.domain.Stock;
import com.flowers.inventoryservice.domain.daocontracts.IFlowerShopDAO;
import com.flowers.inventoryservice.domain.daocontracts.IStockDAO;
import com.flowers.inventoryservice.services.dto.ExpandedStockDTO;
import com.flowers.inventoryservice.services.dto.FlowerDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    private final IStockDAO stockDAO;
    private final IFlowerShopDAO flowerShopDAO;
    private final RestTemplate restTemplate;

    private static final String FLOWER_CATALOG_URL = "http://localhost:8081/api/flowers";

    public StockService(IStockDAO stockDAO, IFlowerShopDAO flowerShopDAO, RestTemplate restTemplate) {
        this.stockDAO = stockDAO;
        this.flowerShopDAO = flowerShopDAO;
        this.restTemplate = restTemplate;
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
        return stockDAO.sellFlower(stockId, quantity);
    }

    /**
     * Obține detalii despre floare din FlowerCatalogService prin REST.
     */
    public FlowerDTO getFlowerDetails(int flowerId) {
        try {
            String url = FLOWER_CATALOG_URL + "/" + flowerId;
            return restTemplate.getForObject(url, FlowerDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returnează stocurile cu detalii extinse (nume floare, preț, nume florărie).
     * Comunică cu FlowerCatalogService prin REST pentru detalii despre flori.
     */
    public List<ExpandedStockDTO> getExpandedStocks() {
        List<Stock> stocks = stockDAO.stockList();
        List<ExpandedStockDTO> expandedList = new ArrayList<>();

        for (Stock stock : stocks) {
            // Obține numele florăriei din baza proprie
            FlowerShop flowerShop = flowerShopDAO.flowerShopById(stock.getFlowerShopId().getFlowerShopId());
            String flowerShopName = (flowerShop != null) ? flowerShop.getName() : "Unknown";

            // Obține detalii despre floare din FlowerCatalogService prin REST
            FlowerDTO flowerDTO = getFlowerDetails(stock.getFlowerId());
            String flowerName = (flowerDTO != null) ? flowerDTO.getName() : "Unknown";
            float sellingPrice = (flowerDTO != null) ? flowerDTO.getSellingPrice() : 0;

            ExpandedStockDTO expanded = new ExpandedStockDTO(
                    stock.getId().getStockId(),
                    stock.getFlowerShopId().getFlowerShopId(),
                    flowerShopName,
                    stock.getFlowerId(),
                    flowerName,
                    sellingPrice,
                    stock.getColor(),
                    stock.getQuantity()
            );
            expandedList.add(expanded);
        }

        return expandedList;
    }
}
