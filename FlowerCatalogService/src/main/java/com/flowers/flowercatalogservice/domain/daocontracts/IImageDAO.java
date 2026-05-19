package com.flowers.flowercatalogservice.domain.daocontracts;

import com.flowers.flowercatalogservice.domain.Image;

import java.util.List;

public interface IImageDAO {

    List<Image> imageList();

    List<Image> imagesByFlowerId(int flowerId);

    boolean insert(Image image);

    boolean update(Image image);

    boolean delete(int id);
}
