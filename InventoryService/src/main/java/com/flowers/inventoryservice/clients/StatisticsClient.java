package com.flowers.inventoryservice.clients;

import com.flowers.inventoryservice.events.SaleCompletedEvent;
import com.flowers.inventoryservice.services.dto.FlowerDTO;

public interface StatisticsClient {

    void recordSale(SaleCompletedEvent event, FlowerDTO flower);
}
