package com.flowers.statisticsservice.domain;

import java.util.Date;

public class SaleStatistic {

    private int id;
    private int flowerId;
    private String flowerName;
    private int flowerShopId;
    private int quantitySold;
    private float revenue;
    private float profit;
    private Date saleDate;

    public SaleStatistic() {}

    public SaleStatistic(int id, int flowerId, String flowerName, int flowerShopId, int quantitySold, float revenue, float profit, Date saleDate) {
        this.id = id;
        this.flowerId = flowerId;
        this.flowerName = flowerName;
        this.flowerShopId = flowerShopId;
        this.quantitySold = quantitySold;
        this.revenue = revenue;
        this.profit = profit;
        this.saleDate = saleDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFlowerId() { return flowerId; }
    public void setFlowerId(int flowerId) { this.flowerId = flowerId; }
    public String getFlowerName() { return flowerName; }
    public void setFlowerName(String flowerName) { this.flowerName = flowerName; }
    public int getFlowerShopId() { return flowerShopId; }
    public void setFlowerShopId(int flowerShopId) { this.flowerShopId = flowerShopId; }
    public int getQuantitySold() { return quantitySold; }
    public void setQuantitySold(int quantitySold) { this.quantitySold = quantitySold; }
    public float getRevenue() { return revenue; }
    public void setRevenue(float revenue) { this.revenue = revenue; }
    public float getProfit() { return profit; }
    public void setProfit(float profit) { this.profit = profit; }
    public Date getSaleDate() { return saleDate; }
    public void setSaleDate(Date saleDate) { this.saleDate = saleDate; }
}
