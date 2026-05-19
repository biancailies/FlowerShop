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

    private static final String FLOWER_IMAGES_URL = "http://localhost:8081/api/images/flower";
    private static final String STATISTICS_URL = "http://localhost:8084/api/statistics/sale";

    public boolean sellFlower(int stockId, int quantity) {
        Stock stock = stockDAO.stockById(stockId);
        if (stock == null) return false;
        if (stock.getQuantity() < quantity) return false;
        
        boolean success = stockDAO.sellFlower(stockId, quantity);
        if (success) {
            try {
                // Post to statistics service
                FlowerDTO flower = getFlowerDetails(stock.getFlowerId());
                if (flower != null) {
                    float revenue = flower.getSellingPrice() * quantity;
                    float profit = (flower.getSellingPrice() - flower.getPurchasePrice()) * quantity;

                    java.util.Map<String, Object> saleRecord = new java.util.HashMap<>();
                    saleRecord.put("flowerId", stock.getFlowerId());
                    saleRecord.put("flowerName", flower.getName());
                    saleRecord.put("flowerShopId", stock.getFlowerShopId().getFlowerShopId());
                    saleRecord.put("quantitySold", quantity);
                    saleRecord.put("revenue", revenue);
                    saleRecord.put("profit", profit);

                    restTemplate.postForEntity(STATISTICS_URL, saleRecord, String.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public FlowerDTO getFlowerDetails(int flowerId) {
        try {
            String url = FLOWER_CATALOG_URL + "/" + flowerId;
            return restTemplate.getForObject(url, FlowerDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFlowerImageUrl(int flowerId) {
        try {
            String url = FLOWER_IMAGES_URL + "/" + flowerId;
            java.util.List<java.util.Map<String, Object>> images = restTemplate.getForObject(url, java.util.List.class);
            if (images != null && !images.isEmpty()) {
                return (String) images.get(0).get("url");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // fallback
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
