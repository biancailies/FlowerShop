package com.flowers.flowercatalogservice.domain;

public class Flower {

    private FlowerId id;
    private String name;
    private float purchasePrice;
    private float sellingPrice;

    public Flower() {
    }

    public Flower(FlowerId id, String name, float purchasePrice, float sellingPrice) {
        this.id = id;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    public Flower(String name, float purchasePrice, float sellingPrice) {
        this.id = new FlowerId(0);
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    public FlowerId getId() {
        return id;
    }

    public void setId(FlowerId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public String toString() {
        return "Flower{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", sellingPrice=" + sellingPrice +
                '}';
    }
}
