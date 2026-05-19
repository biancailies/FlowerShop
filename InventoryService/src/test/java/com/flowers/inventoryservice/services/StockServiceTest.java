package com.flowers.inventoryservice.services;

import com.flowers.inventoryservice.clients.FlowerCatalogClient;
import com.flowers.inventoryservice.domain.FlowerShopId;
import com.flowers.inventoryservice.domain.Stock;
import com.flowers.inventoryservice.domain.StockId;
import com.flowers.inventoryservice.domain.daocontracts.IFlowerShopDAO;
import com.flowers.inventoryservice.domain.daocontracts.IStockDAO;
import com.flowers.inventoryservice.events.SaleCompletedEvent;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StockServiceTest {

    @Test
    void sellFlowerPublishesSaleCompletedEventAfterSuccessfulStockUpdate() {
        IStockDAO stockDAO = mock(IStockDAO.class);
        IFlowerShopDAO flowerShopDAO = mock(IFlowerShopDAO.class);
        FlowerCatalogClient flowerCatalogClient = mock(FlowerCatalogClient.class);
        ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);
        StockService service = new StockService(stockDAO, flowerShopDAO, flowerCatalogClient, publisher);
        Stock stock = new Stock(new StockId(9), new FlowerShopId(2), 4, "red", 10);

        when(stockDAO.stockById(9)).thenReturn(stock);
        when(stockDAO.sellFlower(9, 3)).thenReturn(true);

        assertTrue(service.sellFlower(9, 3));

        ArgumentCaptor<SaleCompletedEvent> captor = ArgumentCaptor.forClass(SaleCompletedEvent.class);
        verify(publisher).publishEvent(captor.capture());
        assertEquals(9, captor.getValue().getStockId());
        assertEquals(4, captor.getValue().getFlowerId());
        assertEquals(2, captor.getValue().getFlowerShopId());
        assertEquals(3, captor.getValue().getQuantity());
    }

    @Test
    void sellFlowerRejectsInsufficientQuantityWithoutPublishingEvent() {
        IStockDAO stockDAO = mock(IStockDAO.class);
        StockService service = new StockService(
                stockDAO,
                mock(IFlowerShopDAO.class),
                mock(FlowerCatalogClient.class),
                mock(ApplicationEventPublisher.class)
        );
        Stock stock = new Stock(new StockId(9), new FlowerShopId(2), 4, "red", 1);

        when(stockDAO.stockById(9)).thenReturn(stock);

        assertFalse(service.sellFlower(9, 3));
        verify(stockDAO, never()).sellFlower(9, 3);
    }
}
