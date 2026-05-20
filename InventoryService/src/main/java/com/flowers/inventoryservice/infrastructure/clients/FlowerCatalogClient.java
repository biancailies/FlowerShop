package com.flowers.inventoryservice.infrastructure.clients;

import com.flowers.inventoryservice.services.dto.FlowerDTO;

public interface FlowerCatalogClient {

    FlowerDTO getFlowerById(int flowerId);

    String getFlowerImageUrl(int flowerId);
}
