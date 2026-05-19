package com.flowers.inventoryservice.infrastructure.tableentities;

import com.flowers.inventoryservice.domain.FlowerShopId;
import com.flowers.inventoryservice.domain.Stock;
import com.flowers.inventoryservice.domain.StockId;
import jakarta.persistence.*;

@Entity
@Table(name = "stocks")
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "flower_shop_id", nullable = false)
    private int flowerShopId;

    @Column(name = "flower_id", nullable = false)
    private int flowerId;

    @Column(name = "color", length = 50)
    private String color;

    @Column(name = "quantity")
    private int quantity;

    public StockEntity() {
    }

    public StockEntity(Stock domainStock) {
        if (domainStock.getId() != null && domainStock.getId().getStockId() > 0) {
            this.id = domainStock.getId().getStockId();
        } else {
            this.id = null;
        }
        this.flowerShopId = domainStock.getFlowerShopId().getFlowerShopId();
        this.flowerId = domainStock.getFlowerId();
        this.color = domainStock.getColor();
        this.quantity = domainStock.getQuantity();
    }

    public Stock toStock() {
        return new Stock(
                new StockId(this.id),
                new FlowerShopId(this.flowerShopId),
                this.flowerId,
                this.color,
                this.quantity
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFlowerShopId() {
        return flowerShopId;
    }

    public void setFlowerShopId(int flowerShopId) {
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
}
