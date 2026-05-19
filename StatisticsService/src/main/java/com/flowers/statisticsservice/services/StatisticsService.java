package com.flowers.statisticsservice.services;

import com.flowers.statisticsservice.domain.Statistics;
import com.flowers.statisticsservice.domain.daocontracts.IStatisticsDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private final IStatisticsDAO statisticsDAO;

    public StatisticsService(IStatisticsDAO statisticsDAO) {
        this.statisticsDAO = statisticsDAO;
    }

    public List<Statistics> getStatistics() {
        return statisticsDAO.statisticsList();
    }

    public List<Statistics> getStatisticsByFlowerShop(int flowerShopId) {
        return statisticsDAO.statisticsByFlowerShop(flowerShopId);
    }

    public Statistics getStatistic(int id) {
        return statisticsDAO.searchStatistic(id);
    }

    public boolean insertStatistics(Statistics statistic) {
        return statisticsDAO.insert(statistic);
    }

    public boolean updateStatistics(Statistics statistic) {
        return statisticsDAO.update(statistic);
    }

    public boolean deleteStatistics(int id) {
        return statisticsDAO.delete(id);
    }
}
