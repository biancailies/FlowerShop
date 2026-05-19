package com.flowers.statisticsservice.domain.daocontracts;

import com.flowers.statisticsservice.domain.SaleStatistic;
import java.util.List;

public interface ISaleStatisticDAO {
    boolean insert(SaleStatistic saleStatistic);
    List<SaleStatistic> getAll();
}
