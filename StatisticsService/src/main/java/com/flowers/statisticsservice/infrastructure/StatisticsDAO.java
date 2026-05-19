package com.flowers.statisticsservice.infrastructure;

import com.flowers.statisticsservice.domain.Statistics;
import com.flowers.statisticsservice.domain.daocontracts.IStatisticsDAO;
import com.flowers.statisticsservice.infrastructure.repositories.StatisticsJpaRepository;
import com.flowers.statisticsservice.infrastructure.tableentities.StatisticsEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StatisticsDAO implements IStatisticsDAO {

    private final StatisticsJpaRepository statisticsJpaRepository;

    public StatisticsDAO(StatisticsJpaRepository statisticsJpaRepository) {
        this.statisticsJpaRepository = statisticsJpaRepository;
    }

    @Override
    public List<Statistics> statisticsList() {
        return statisticsJpaRepository.findAll()
                .stream()
                .map(StatisticsEntity::toStatistics)
                .collect(Collectors.toList());
    }

    @Override
    public List<Statistics> statisticsByFlowerShop(int flowerShopId) {
        return statisticsJpaRepository.findByFlowerShopId(flowerShopId)
                .stream()
                .map(StatisticsEntity::toStatistics)
                .collect(Collectors.toList());
    }

    @Override
    public Statistics searchStatistic(int id) {
        Optional<StatisticsEntity> entity = statisticsJpaRepository.findById(id);
        return entity.map(StatisticsEntity::toStatistics).orElse(null);
    }

    @Override
    public boolean insert(Statistics statistic) {
        try {
            StatisticsEntity entity = new StatisticsEntity(statistic);
            statisticsJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Statistics statistic) {
        try {
            if (statistic.getId() == null || !statisticsJpaRepository.existsById(statistic.getId().getStatisticsId())) {
                return false;
            }
            StatisticsEntity entity = new StatisticsEntity(statistic);
            statisticsJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            if (!statisticsJpaRepository.existsById(id)) {
                return false;
            }
            statisticsJpaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
