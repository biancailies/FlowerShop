package com.flowers.statisticsservice.domain;

public class StatisticsId {

    private int statisticsId;

    public StatisticsId() {
    }

    public StatisticsId(int statisticsId) {
        this.statisticsId = statisticsId;
    }

    public int getStatisticsId() {
        return statisticsId;
    }

    public void setStatisticsId(int statisticsId) {
        this.statisticsId = statisticsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticsId that = (StatisticsId) o;
        return statisticsId == that.statisticsId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(statisticsId);
    }

    @Override
    public String toString() {
        return "StatisticsId{" + "statisticsId=" + statisticsId + '}';
    }
}
