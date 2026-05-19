package com.flowers.statisticsservice.infrastructure.tableentities;

import com.flowers.statisticsservice.domain.Statistics;
import com.flowers.statisticsservice.domain.StatisticsId;
import jakarta.persistence.*;

@Entity
@Table(name = "`Statistics`")
public class StatisticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`Id`")
    private Integer id;

    @Column(name = "`FlowerShopId`", nullable = false)
    private int flowerShopId;

    @Column(name = "`Statistics`", nullable = false)
    private float statistics;

    @Column(name = "`Type`", nullable = false, length = 100)
    private String type;

    public StatisticsEntity() {
    }

    public StatisticsEntity(Statistics domainStatistics) {
        if (domainStatistics.getId() != null && domainStatistics.getId().getStatisticsId() > 0) {
            this.id = domainStatistics.getId().getStatisticsId();
        } else {
            this.id = null;
        }
        this.flowerShopId = domainStatistics.getFlowerShopId();
        this.statistics = domainStatistics.getStatistics();
        this.type = domainStatistics.getType();
    }

    public Statistics toStatistics() {
        return new Statistics(
                new StatisticsId(this.id),
                this.flowerShopId,
                this.statistics,
                this.type
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
}
