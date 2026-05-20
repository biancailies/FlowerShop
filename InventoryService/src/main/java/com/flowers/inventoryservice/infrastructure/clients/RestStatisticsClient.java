package com.flowers.inventoryservice.infrastructure.clients;

import com.flowers.inventoryservice.services.events.SaleCompletedEvent;
import com.flowers.inventoryservice.services.dto.FlowerDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestStatisticsClient implements StatisticsClient {

    private final RestTemplate restTemplate;
    private final String statisticsSaleUrl;

    public RestStatisticsClient(
            RestTemplate restTemplate,
            @Value("${services.statistics.sale-url:http://localhost:8084/api/statistics/sale}") String statisticsSaleUrl) {
        this.restTemplate = restTemplate;
        this.statisticsSaleUrl = statisticsSaleUrl;
    }

    @Override
    public void recordSale(SaleCompletedEvent event, FlowerDTO flower) {
        float revenue = flower.getSellingPrice() * event.getQuantity();
        float profit = (flower.getSellingPrice() - flower.getPurchasePrice()) * event.getQuantity();

        Map<String, Object> saleRecord = new HashMap<>();
        saleRecord.put("flowerId", event.getFlowerId());
        saleRecord.put("flowerName", flower.getName());
        saleRecord.put("flowerShopId", event.getFlowerShopId());
        saleRecord.put("quantitySold", event.getQuantity());
        saleRecord.put("revenue", revenue);
        saleRecord.put("profit", profit);

        restTemplate.postForEntity(statisticsSaleUrl, saleRecord, String.class);
    }
}
