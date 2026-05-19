package com.flowers.inventoryservice.controllers;

import com.flowers.inventoryservice.domain.FlowerShopId;
import com.flowers.inventoryservice.domain.Stock;
import com.flowers.inventoryservice.domain.StockId;
import com.flowers.inventoryservice.services.StockService;
import com.flowers.inventoryservice.services.dto.ExpandedStockDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> list = stockService.getAllStocks();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable int id) {
        Stock stock = stockService.getStockById(id);
        if (stock == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stock);
    }

    @GetMapping("/flower-shop/{flowerShopId}")
    public ResponseEntity<List<Stock>> getStocksByFlowerShopId(@PathVariable int flowerShopId) {
        List<Stock> list = stockService.getStocksByFlowerShopId(flowerShopId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/flower/{flowerId}")
    public ResponseEntity<List<Stock>> getStocksByFlowerId(@PathVariable int flowerId) {
        List<Stock> list = stockService.getStocksByFlowerId(flowerId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Stock>> filterStocks(
            @RequestParam(required = false) Integer flowerShopId,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer minQuantity) {
        List<Stock> list = stockService.filterStocks(flowerShopId, color, minQuantity);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/expanded")
    public ResponseEntity<List<ExpandedStockDTO>> getExpandedStocks() {
        List<ExpandedStockDTO> list = stockService.getExpandedStocks();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<String> createStock(@RequestBody Stock stock) {
        if (stock.getId() == null) {
            stock.setId(new StockId(0));
        }
        if (stock.getFlowerShopId() == null) {
            return ResponseEntity.badRequest().body("Flower shop ID is required.");
        }
        boolean result = stockService.createStock(stock);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Stock created successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create stock.");
    }

    @PutMapping
    public ResponseEntity<String> updateStock(@RequestBody Stock stock) {
        if (stock.getId() == null) {
            return ResponseEntity.badRequest().body("Stock ID is required for update.");
        }
        boolean result = stockService.updateStock(stock);
        if (result) {
            return ResponseEntity.ok("Stock updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found or update failed.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable int id) {
        boolean result = stockService.deleteStock(id);
        if (result) {
            return ResponseEntity.ok("Stock deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found or delete failed.");
    }

    @PutMapping("/sell/{stockId}/{quantity}")
    public ResponseEntity<String> sellFlower(@PathVariable int stockId, @PathVariable int quantity) {
        boolean result = stockService.sellFlower(stockId, quantity);
        if (result) {
            return ResponseEntity.ok("Flower sold successfully. Quantity updated.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sale failed. Stock not found or insufficient quantity.");
    }
}
