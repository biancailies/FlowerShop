package com.flowers.statisticsservice.services.dto;

public class StatisticsSummaryDTO {
    private int totalFlowersSold;
    private float totalRevenue;
    private float totalProfit;

    public StatisticsSummaryDTO(int totalFlowersSold, float totalRevenue, float totalProfit) {
        this.totalFlowersSold = totalFlowersSold;
        this.totalRevenue = totalRevenue;
        this.totalProfit = totalProfit;
    }

    public int getTotalFlowersSold() { return totalFlowersSold; }
    public void setTotalFlowersSold(int totalFlowersSold) { this.totalFlowersSold = totalFlowersSold; }
    public float getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(float totalRevenue) { this.totalRevenue = totalRevenue; }
    public float getTotalProfit() { return totalProfit; }
    public void setTotalProfit(float totalProfit) { this.totalProfit = totalProfit; }
}
