package com.flowers.inventoryservice.services.dto;

public class ExpandedStockDTO {

    private int stockId;
    private int flowerShopId;
    private String flowerShopName;
    private int flowerId;
    private String flowerName;
    private float sellingPrice;
    private String color;
    private int quantity;

    public ExpandedStockDTO() {
    }

    public ExpandedStockDTO(int stockId, int flowerShopId, String flowerShopName,
                            int flowerId, String flowerName, float sellingPrice,
                            String color, int quantity) {
        this.stockId = stockId;
        this.flowerShopId = flowerShopId;
        this.flowerShopName = flowerShopName;
        this.flowerId = flowerId;
        this.flowerName = flowerName;
        this.sellingPrice = sellingPrice;
        this.color = color;
        this.quantity = quantity;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getFlowerShopId() {
        return flowerShopId;
    }

    public void setFlowerShopId(int flowerShopId) {
        this.flowerShopId = flowerShopId;
    }

    public String getFlowerShopName() {
        return flowerShopName;
    }

    public void setFlowerShopName(String flowerShopName) {
        this.flowerShopName = flowerShopName;
    }

    public int getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(int flowerId) {
        this.flowerId = flowerId;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
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
}
