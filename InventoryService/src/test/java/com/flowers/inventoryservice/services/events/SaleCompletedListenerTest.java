package com.flowers.inventoryservice.services.events;

import com.flowers.inventoryservice.infrastructure.clients.FlowerCatalogClient;
import com.flowers.inventoryservice.infrastructure.clients.StatisticsClient;
import com.flowers.inventoryservice.services.dto.FlowerDTO;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SaleCompletedListenerTest {

    @Test
    void onSaleCompletedRecordsStatisticsWhenFlowerExists() {
        FlowerCatalogClient flowerCatalogClient = mock(FlowerCatalogClient.class);
        StatisticsClient statisticsClient = mock(StatisticsClient.class);
        SaleCompletedListener listener = new SaleCompletedListener(flowerCatalogClient, statisticsClient);
        SaleCompletedEvent event = new SaleCompletedEvent(1, 2, 3, 4);
        FlowerDTO flower = new FlowerDTO(2, "Rose", 5, 10);

        when(flowerCatalogClient.getFlowerById(2)).thenReturn(flower);

        listener.onSaleCompleted(event);

        verify(statisticsClient).recordSale(event, flower);
    }

    @Test
    void onSaleCompletedSkipsStatisticsWhenFlowerIsMissing() {
        FlowerCatalogClient flowerCatalogClient = mock(FlowerCatalogClient.class);
        StatisticsClient statisticsClient = mock(StatisticsClient.class);
        SaleCompletedListener listener = new SaleCompletedListener(flowerCatalogClient, statisticsClient);
        SaleCompletedEvent event = new SaleCompletedEvent(1, 2, 3, 4);

        when(flowerCatalogClient.getFlowerById(2)).thenReturn(null);

        listener.onSaleCompleted(event);

        verify(statisticsClient, never()).recordSale(event, null);
    }
}
