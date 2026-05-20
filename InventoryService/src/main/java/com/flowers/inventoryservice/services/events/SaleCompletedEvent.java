package com.flowers.inventoryservice.services.events;

public class SaleCompletedEvent {

    private final int stockId;
    private final int flowerId;
    private final int flowerShopId;
    private final int quantity;

    public SaleCompletedEvent(int stockId, int flowerId, int flowerShopId, int quantity) {
        this.stockId = stockId;
        this.flowerId = flowerId;
        this.flowerShopId = flowerShopId;
        this.quantity = quantity;
    }

    public int getStockId() {
        return stockId;
    }

    public int getFlowerId() {
        return flowerId;
    }

    public int getFlowerShopId() {
        return flowerShopId;
    }

    public int getQuantity() {
        return quantity;
    }
}
