package com.flowers.flowercatalogservice.services;

import com.flowers.flowercatalogservice.domain.Flower;
import com.flowers.flowercatalogservice.domain.daocontracts.IFlowerDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowerService {

    private final IFlowerDAO flowerDAO;

    public FlowerService(IFlowerDAO flowerDAO) {
        this.flowerDAO = flowerDAO;
    }

    public List<Flower> getFlowers() {
        return flowerDAO.flowerList();
    }

    public List<Flower> getFlowersByName(String name) {
        return flowerDAO.flowerFilterByName(name);
    }

    public Flower getFlowerById(int id) {
        return flowerDAO.flowerById(id);
    }

    public boolean insertFlower(Flower flower) {
        return flowerDAO.insert(flower);
    }

    public boolean updateFlower(Flower flower) {
        return flowerDAO.update(flower);
    }

    public boolean deleteFlower(int id) {
        return flowerDAO.delete(id);
    }
}
