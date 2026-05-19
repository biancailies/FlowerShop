package com.flowers.statisticsservice.domain;

public class Statistics {

    private StatisticsId id;
    private int flowerShopId;
    private float statistics;
    private String type;

    public Statistics() {
    }

    public Statistics(StatisticsId id, int flowerShopId, float statistics, String type) {
        this.id = id;
        this.flowerShopId = flowerShopId;
        this.statistics = statistics;
        this.type = type;
    }

    public Statistics(int flowerShopId, float statistics, String type) {
        this.id = new StatisticsId(0);
        this.flowerShopId = flowerShopId;
        this.statistics = statistics;
        this.type = type;
    }

    public StatisticsId getId() {
        return id;
    }

    public void setId(StatisticsId id) {
        this.id = id;
    }

    public int getFlowerShopId() {
        return flowerShopId;
    }

    public void setFlowerShopId(int flowerShopId) {
        this.flowerShopId = flowerShopId;
    }

    public float getStatistics() {
        return statistics;
    }

    public void setStatistics(float statistics) {
        this.statistics = statistics;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", flowerShopId=" + flowerShopId +
                ", statistics=" + statistics +
                ", type='" + type + '\'' +
                '}';
    }
}
