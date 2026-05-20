package com.flowers.inventoryservice.infrastructure.clients;

import com.flowers.inventoryservice.services.dto.FlowerDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class RestFlowerCatalogClient implements FlowerCatalogClient {

    private final RestTemplate restTemplate;
    private final String flowerCatalogUrl;
    private final String flowerImagesUrl;

    public RestFlowerCatalogClient(
            RestTemplate restTemplate,
            @Value("${services.flower-catalog.flowers-url:http://localhost:8081/api/flowers}") String flowerCatalogUrl,
            @Value("${services.flower-catalog.images-url:http://localhost:8081/api/images/flower}") String flowerImagesUrl) {
        this.restTemplate = restTemplate;
        this.flowerCatalogUrl = flowerCatalogUrl;
        this.flowerImagesUrl = flowerImagesUrl;
    }

    @Override
    public FlowerDTO getFlowerById(int flowerId) {
        try {
            return restTemplate.getForObject(flowerCatalogUrl + "/" + flowerId, FlowerDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getFlowerImageUrl(int flowerId) {
        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    flowerImagesUrl + "/" + flowerId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            List<Map<String, Object>> images = response.getBody();
            if (images != null && !images.isEmpty()) {
                return (String) images.get(0).get("url");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
