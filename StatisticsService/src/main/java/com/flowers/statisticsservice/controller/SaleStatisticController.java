package com.flowers.statisticsservice.controller;

import com.flowers.statisticsservice.domain.SaleStatistic;
import com.flowers.statisticsservice.services.SaleStatisticService;
import com.flowers.statisticsservice.services.dto.StatisticsSummaryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics/sale")
public class SaleStatisticController {

    private final SaleStatisticService service;

    public SaleStatisticController(SaleStatisticService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createSaleRecord(@RequestBody SaleStatistic record) {
        boolean created = service.addSaleStatistic(record);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<SaleStatistic>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/summary")
    public ResponseEntity<StatisticsSummaryDTO> getSummary() {
        return ResponseEntity.ok(service.getSummary());
    }
}
