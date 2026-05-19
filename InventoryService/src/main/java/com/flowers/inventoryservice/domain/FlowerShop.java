package com.flowers.inventoryservice.domain;

public class FlowerShop {

    private FlowerShopId id;
    private String name;
    private String address;

    public FlowerShop() {
    }

    public FlowerShop(FlowerShopId id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public FlowerShop(String name, String address) {
        this.id = new FlowerShopId(0);
        this.name = name;
        this.address = address;
    }

    public FlowerShopId getId() {
        return id;
    }

    public void setId(FlowerShopId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "FlowerShop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
