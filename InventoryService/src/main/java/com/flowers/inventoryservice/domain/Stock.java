package com.flowers.inventoryservice.domain;

public class Stock {

    private StockId id;
    private FlowerShopId flowerShopId;
    private int flowerId;
    private String color;
    private int quantity;

    public Stock() {
    }

    public Stock(StockId id, FlowerShopId flowerShopId, int flowerId, String color, int quantity) {
        this.id = id;
        this.flowerShopId = flowerShopId;
        this.flowerId = flowerId;
        this.color = color;
        this.quantity = quantity;
    }

    public Stock(FlowerShopId flowerShopId, int flowerId, String color, int quantity) {
        this.id = new StockId(0);
        this.flowerShopId = flowerShopId;
        this.flowerId = flowerId;
        this.color = color;
        this.quantity = quantity;
    }

    public StockId getId() {
        return id;
    }

    public void setId(StockId id) {
        this.id = id;
    }

    public FlowerShopId getFlowerShopId() {
        return flowerShopId;
    }

    public void setFlowerShopId(FlowerShopId flowerShopId) {
        this.flowerShopId = flowerShopId;
    }

    public int getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(int flowerId) {
        this.flowerId = flowerId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", flowerShopId=" + flowerShopId +
                ", flowerId=" + flowerId +
                ", color='" + color + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
