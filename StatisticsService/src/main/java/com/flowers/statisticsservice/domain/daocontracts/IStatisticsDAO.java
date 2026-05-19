package com.flowers.statisticsservice.domain.daocontracts;

import com.flowers.statisticsservice.domain.Statistics;

import java.util.List;

public interface IStatisticsDAO {

    List<Statistics> statisticsList();

    List<Statistics> statisticsByFlowerShop(int flowerShopId);

    Statistics searchStatistic(int id);

    boolean insert(Statistics statistic);

    boolean update(Statistics statistic);

    boolean delete(int id);
}
