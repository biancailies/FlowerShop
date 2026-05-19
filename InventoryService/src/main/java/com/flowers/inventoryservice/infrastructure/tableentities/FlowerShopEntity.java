package com.flowers.inventoryservice.infrastructure.tableentities;

import com.flowers.inventoryservice.domain.FlowerShop;
import com.flowers.inventoryservice.domain.FlowerShopId;
import jakarta.persistence.*;

@Entity
@Table(name = "flower_shops")
public class FlowerShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    public FlowerShopEntity() {
    }

    public FlowerShopEntity(FlowerShop domainFlowerShop) {
        if (domainFlowerShop.getId() != null && domainFlowerShop.getId().getFlowerShopId() > 0) {
            this.id = domainFlowerShop.getId().getFlowerShopId();
        } else {
            this.id = null;
        }
        this.name = domainFlowerShop.getName();
        this.address = domainFlowerShop.getAddress();
    }

    public FlowerShop toFlowerShop() {
        return new FlowerShop(
                new FlowerShopId(this.id),
                this.name,
                this.address
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
