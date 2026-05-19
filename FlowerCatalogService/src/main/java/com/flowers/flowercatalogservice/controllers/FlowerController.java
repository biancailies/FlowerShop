package com.flowers.flowercatalogservice.controllers;

import com.flowers.flowercatalogservice.domain.Flower;
import com.flowers.flowercatalogservice.domain.FlowerId;
import com.flowers.flowercatalogservice.services.FlowerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flowers")
public class FlowerController {

    private final FlowerService flowerService;

    public FlowerController(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @GetMapping
    public ResponseEntity<List<Flower>> getAllFlowers() {
        List<Flower> list = flowerService.getFlowers();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flower> getFlowerById(@PathVariable int id) {
        Flower flower = flowerService.getFlowerById(id);
        if (flower == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flower);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Flower>> searchFlowersByName(@PathVariable String name) {
        List<Flower> list = flowerService.getFlowersByName(name);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<String> insertFlower(@RequestBody Flower flower) {
        if (flower.getId() == null) {
            flower.setId(new FlowerId(0));
        }
        boolean result = flowerService.insertFlower(flower);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Flower inserted successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to insert flower.");
    }

    @PutMapping
    public ResponseEntity<String> updateFlower(@RequestBody Flower flower) {
        if (flower.getId() == null) {
            return ResponseEntity.badRequest().body("Flower ID is required for update.");
        }
        boolean result = flowerService.updateFlower(flower);
        if (result) {
            return ResponseEntity.ok("Flower updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flower not found or update failed.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlower(@PathVariable int id) {
        boolean result = flowerService.deleteFlower(id);
        if (result) {
            return ResponseEntity.ok("Flower deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flower not found or delete failed.");
    }
}
