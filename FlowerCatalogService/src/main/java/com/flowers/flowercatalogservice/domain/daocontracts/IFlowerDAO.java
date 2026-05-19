package com.flowers.flowercatalogservice.domain.daocontracts;

import com.flowers.flowercatalogservice.domain.Flower;

import java.util.List;

public interface IFlowerDAO {

    List<Flower> flowerList();

    List<Flower> flowerFilterByName(String search);

    Flower flowerById(int id);

    boolean insert(Flower flower);

    boolean update(Flower flower);

    boolean delete(int id);
}
