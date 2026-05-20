package com.flowers.statisticsservice.controller;

import com.flowers.statisticsservice.domain.Statistics;
import com.flowers.statisticsservice.domain.StatisticsId;
import com.flowers.statisticsservice.services.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ResponseEntity<List<Statistics>> getAllStatistics() {
        List<Statistics> list = statisticsService.getStatistics();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Statistics> getStatisticById(@PathVariable int id) {
        Statistics statistic = statisticsService.getStatistic(id);
        if (statistic == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(statistic);
    }

    @GetMapping("/flowershop/{flowerShopId}")
    public ResponseEntity<List<Statistics>> getStatisticsByFlowerShop(@PathVariable int flowerShopId) {
        List<Statistics> list = statisticsService.getStatisticsByFlowerShop(flowerShopId);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<String> insertStatistic(@RequestBody Statistics statistic) {
        if (statistic.getId() == null) {
            statistic.setId(new StatisticsId(0));
        }
        boolean result = statisticsService.insertStatistics(statistic);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Statistic inserted successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to insert statistic.");
    }

    @PutMapping
    public ResponseEntity<String> updateStatistic(@RequestBody Statistics statistic) {
        if (statistic.getId() == null) {
            return ResponseEntity.badRequest().body("Statistic ID is required for update.");
        }
        boolean result = statisticsService.updateStatistics(statistic);
        if (result) {
            return ResponseEntity.ok("Statistic updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Statistic not found or update failed.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStatistic(@PathVariable int id) {
        boolean result = statisticsService.deleteStatistics(id);
        if (result) {
            return ResponseEntity.ok("Statistic deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Statistic not found or delete failed.");
    }
}
