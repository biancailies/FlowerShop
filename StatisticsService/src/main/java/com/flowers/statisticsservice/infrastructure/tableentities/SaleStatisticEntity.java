package com.flowers.statisticsservice.infrastructure.tableentities;

import com.flowers.statisticsservice.domain.SaleStatistic;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sale_statistics")
public class SaleStatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "flower_id")
    private int flowerId;
    
    @Column(name = "flower_name")
    private String flowerName;
    
    @Column(name = "flower_shop_id")
    private int flowerShopId;
    
    @Column(name = "quantity_sold")
    private int quantitySold;
    
    @Column(name = "revenue")
    private float revenue;
    
    @Column(name = "profit")
    private float profit;
    
    @Column(name = "sale_date")
    private Date saleDate;

    public SaleStatisticEntity() {}

    public SaleStatisticEntity(SaleStatistic domain) {
        this.id = domain.getId();
        this.flowerId = domain.getFlowerId();
        this.flowerName = domain.getFlowerName();
        this.flowerShopId = domain.getFlowerShopId();
        this.quantitySold = domain.getQuantitySold();
        this.revenue = domain.getRevenue();
        this.profit = domain.getProfit();
        this.saleDate = domain.getSaleDate();
    }

    public SaleStatistic toDomain() {
        return new SaleStatistic(id, flowerId, flowerName, flowerShopId, quantitySold, revenue, profit, saleDate);
    }
}
