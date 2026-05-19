package com.flowers.inventoryservice.domain;

public class FlowerShopId {

    private int flowerShopId;

    public FlowerShopId() {
    }

    public FlowerShopId(int flowerShopId) {
        this.flowerShopId = flowerShopId;
    }

    public int getFlowerShopId() {
        return flowerShopId;
    }

    public void setFlowerShopId(int flowerShopId) {
        this.flowerShopId = flowerShopId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlowerShopId that = (FlowerShopId) o;
        return flowerShopId == that.flowerShopId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(flowerShopId);
    }

    @Override
    public String toString() {
        return "FlowerShopId{" + "flowerShopId=" + flowerShopId + '}';
    }
}
