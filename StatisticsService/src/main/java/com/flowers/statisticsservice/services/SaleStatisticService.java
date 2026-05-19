package com.flowers.statisticsservice.services;

import com.flowers.statisticsservice.domain.SaleStatistic;
import com.flowers.statisticsservice.domain.daocontracts.ISaleStatisticDAO;
import com.flowers.statisticsservice.services.dto.StatisticsSummaryDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SaleStatisticService {

    private final ISaleStatisticDAO dao;

    public SaleStatisticService(ISaleStatisticDAO dao) {
        this.dao = dao;
    }

    public boolean addSaleStatistic(SaleStatistic stat) {
        if (stat.getSaleDate() == null) {
            stat.setSaleDate(new Date());
        }
        return dao.insert(stat);
    }

    public List<SaleStatistic> getAll() {
        return dao.getAll();
    }

    public StatisticsSummaryDTO getSummary() {
        List<SaleStatistic> allStats = dao.getAll();
        int totalSold = 0;
        float totalRev = 0;
        float totalProf = 0;

        for (SaleStatistic s : allStats) {
            totalSold += s.getQuantitySold();
            totalRev += s.getRevenue();
            totalProf += s.getProfit();
        }

        return new StatisticsSummaryDTO(totalSold, totalRev, totalProf);
    }
}
