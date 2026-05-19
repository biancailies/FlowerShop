package com.flowers.inventoryservice.events;

import com.flowers.inventoryservice.clients.FlowerCatalogClient;
import com.flowers.inventoryservice.clients.StatisticsClient;
import com.flowers.inventoryservice.services.dto.FlowerDTO;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SaleCompletedListener {

    private final FlowerCatalogClient flowerCatalogClient;
    private final StatisticsClient statisticsClient;

    public SaleCompletedListener(FlowerCatalogClient flowerCatalogClient, StatisticsClient statisticsClient) {
        this.flowerCatalogClient = flowerCatalogClient;
        this.statisticsClient = statisticsClient;
    }

    @EventListener
    public void onSaleCompleted(SaleCompletedEvent event) {
        try {
            FlowerDTO flower = flowerCatalogClient.getFlowerById(event.getFlowerId());
            if (flower != null) {
                statisticsClient.recordSale(event, flower);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
