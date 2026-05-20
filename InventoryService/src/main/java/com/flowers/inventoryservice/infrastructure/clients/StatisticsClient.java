package com.flowers.inventoryservice.infrastructure.clients;

import com.flowers.inventoryservice.services.events.SaleCompletedEvent;
import com.flowers.inventoryservice.services.dto.FlowerDTO;

public interface StatisticsClient {

    void recordSale(SaleCompletedEvent event, FlowerDTO flower);
}
