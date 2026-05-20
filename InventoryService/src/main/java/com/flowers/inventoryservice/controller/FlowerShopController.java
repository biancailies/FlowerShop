package com.flowers.inventoryservice.controller;

import com.flowers.inventoryservice.domain.FlowerShop;
import com.flowers.inventoryservice.domain.FlowerShopId;
import com.flowers.inventoryservice.services.FlowerShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flower-shops")
public class FlowerShopController {

    private final FlowerShopService flowerShopService;

    public FlowerShopController(FlowerShopService flowerShopService) {
        this.flowerShopService = flowerShopService;
    }

    @GetMapping
    public ResponseEntity<List<FlowerShop>> getAllFlowerShops() {
        List<FlowerShop> list = flowerShopService.getAllFlowerShops();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlowerShop> getFlowerShopById(@PathVariable int id) {
        FlowerShop flowerShop = flowerShopService.getFlowerShopById(id);
        if (flowerShop == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flowerShop);
    }

    @PostMapping
    public ResponseEntity<String> createFlowerShop(@RequestBody FlowerShop flowerShop) {
        if (flowerShop.getId() == null) {
            flowerShop.setId(new FlowerShopId(0));
        }
        boolean result = flowerShopService.createFlowerShop(flowerShop);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Flower shop created successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create flower shop.");
    }

    @PutMapping
    public ResponseEntity<String> updateFlowerShop(@RequestBody FlowerShop flowerShop) {
        if (flowerShop.getId() == null) {
            return ResponseEntity.badRequest().body("Flower shop ID is required for update.");
        }
        boolean result = flowerShopService.updateFlowerShop(flowerShop);
        if (result) {
            return ResponseEntity.ok("Flower shop updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flower shop not found or update failed.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlowerShop(@PathVariable int id) {
        boolean result = flowerShopService.deleteFlowerShop(id);
        if (result) {
            return ResponseEntity.ok("Flower shop deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flower shop not found or delete failed.");
    }
}
