package com.flowers.statisticsservice.infrastructure;

import com.flowers.statisticsservice.domain.SaleStatistic;
import com.flowers.statisticsservice.domain.daocontracts.ISaleStatisticDAO;
import com.flowers.statisticsservice.infrastructure.repositories.SaleStatisticJpaRepository;
import com.flowers.statisticsservice.infrastructure.tableentities.SaleStatisticEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaleStatisticDAO implements ISaleStatisticDAO {

    private final SaleStatisticJpaRepository repository;

    public SaleStatisticDAO(SaleStatisticJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean insert(SaleStatistic saleStatistic) {
        try {
            repository.save(new SaleStatisticEntity(saleStatistic));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SaleStatistic> getAll() {
        return repository.findAll().stream()
                .map(SaleStatisticEntity::toDomain)
                .collect(Collectors.toList());
    }
}
