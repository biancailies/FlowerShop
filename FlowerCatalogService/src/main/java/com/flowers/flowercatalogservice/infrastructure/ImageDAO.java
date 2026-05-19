package com.flowers.flowercatalogservice.infrastructure;

import com.flowers.flowercatalogservice.domain.Image;
import com.flowers.flowercatalogservice.domain.daocontracts.IImageDAO;
import com.flowers.flowercatalogservice.infrastructure.repositories.ImageJpaRepository;
import com.flowers.flowercatalogservice.infrastructure.tableentities.ImageEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageDAO implements IImageDAO {

    private final ImageJpaRepository imageJpaRepository;

    public ImageDAO(ImageJpaRepository imageJpaRepository) {
        this.imageJpaRepository = imageJpaRepository;
    }

    @Override
    public List<Image> imageList() {
        return imageJpaRepository.findAll()
                .stream()
                .map(ImageEntity::toImage)
                .collect(Collectors.toList());
    }

    @Override
    public List<Image> imagesByFlowerId(int flowerId) {
        return imageJpaRepository.findByFlowerId(flowerId)
                .stream()
                .map(ImageEntity::toImage)
                .collect(Collectors.toList());
    }

    @Override
    public boolean insert(Image image) {
        try {
            ImageEntity entity = new ImageEntity(image);
            imageJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Image image) {
        try {
            if (image.getId() == null || !imageJpaRepository.existsById(image.getId().getImageId())) {
                return false;
            }
            ImageEntity entity = new ImageEntity(image);
            imageJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            if (!imageJpaRepository.existsById(id)) {
                return false;
            }
            imageJpaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
