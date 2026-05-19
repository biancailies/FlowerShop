package com.flowers.inventoryservice.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * DTO pentru deserializarea răspunsului de la FlowerCatalogService.
 * Flower-ul din FlowerCatalogService are id ca obiect: { "flowerId": 1 }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowerDTO {

    private int id;
    private String name;
    private float purchasePrice;
    private float sellingPrice;

    public FlowerDTO() {
    }

    public FlowerDTO(int id, String name, float purchasePrice, float sellingPrice) {
        this.id = id;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    /**
     * Deserializează câmpul "id" care vine ca obiect JSON: {"flowerId": 1}
     */
    @SuppressWarnings("unchecked")
    public void setId(Object id) {
        if (id instanceof Map) {
            Map<String, Object> idMap = (Map<String, Object>) id;
            Object flowerId = idMap.get("flowerId");
            if (flowerId instanceof Number) {
                this.id = ((Number) flowerId).intValue();
            }
        } else if (id instanceof Number) {
            this.id = ((Number) id).intValue();
        }
    }

    public int getId() {
        return id;
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
}
