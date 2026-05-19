package com.flowers.inventoryservice.domain;

public class StockId {

    private int stockId;

    public StockId() {
    }

    public StockId(int stockId) {
        this.stockId = stockId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockId that = (StockId) o;
        return stockId == that.stockId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(stockId);
    }

    @Override
    public String toString() {
        return "StockId{" + "stockId=" + stockId + '}';
    }
}
