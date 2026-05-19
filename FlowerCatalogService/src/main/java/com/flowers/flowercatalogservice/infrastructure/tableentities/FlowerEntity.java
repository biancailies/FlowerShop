package com.flowers.flowercatalogservice.infrastructure.tableentities;

import com.flowers.flowercatalogservice.domain.Flower;
import com.flowers.flowercatalogservice.domain.FlowerId;
import jakarta.persistence.*;

@Entity
@Table(name = "flowers")
public class FlowerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "purchase_price", nullable = false)
    private float purchasePrice;

    @Column(name = "selling_price", nullable = false)
    private float sellingPrice;

    public FlowerEntity() {
    }

    public FlowerEntity(Flower domainFlower) {
        if (domainFlower.getId() != null && domainFlower.getId().getFlowerId() > 0) {
            this.id = domainFlower.getId().getFlowerId();
        } else {
            this.id = null;
        }
        this.name = domainFlower.getName();
        this.purchasePrice = domainFlower.getPurchasePrice();
        this.sellingPrice = domainFlower.getSellingPrice();
    }

    public Flower toFlower() {
        return new Flower(
                new FlowerId(this.id),
                this.name,
                this.purchasePrice,
                this.sellingPrice
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
