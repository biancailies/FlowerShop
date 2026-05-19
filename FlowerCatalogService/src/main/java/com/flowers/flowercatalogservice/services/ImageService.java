package com.flowers.flowercatalogservice.services;

import com.flowers.flowercatalogservice.domain.Image;
import com.flowers.flowercatalogservice.domain.daocontracts.IImageDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private final IImageDAO imageDAO;

    public ImageService(IImageDAO imageDAO) {
        this.imageDAO = imageDAO;
    }

    public List<Image> getImages() {
        return imageDAO.imageList();
    }

    public List<Image> getImagesByFlowerId(int flowerId) {
        return imageDAO.imagesByFlowerId(flowerId);
    }

    public boolean insertImage(Image image) {
        return imageDAO.insert(image);
    }

    public boolean updateImage(Image image) {
        return imageDAO.update(image);
    }

    public boolean deleteImage(int id) {
        return imageDAO.delete(id);
    }
}
