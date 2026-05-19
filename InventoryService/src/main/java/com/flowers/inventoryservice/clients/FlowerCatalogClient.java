package com.flowers.inventoryservice.clients;

import com.flowers.inventoryservice.services.dto.FlowerDTO;

public interface FlowerCatalogClient {

    FlowerDTO getFlowerById(int flowerId);

    String getFlowerImageUrl(int flowerId);
}
