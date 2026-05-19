package com.flowers.flowercatalogservice.controllers;

import com.flowers.flowercatalogservice.domain.FlowerId;
import com.flowers.flowercatalogservice.domain.Image;
import com.flowers.flowercatalogservice.domain.ImageId;
import com.flowers.flowercatalogservice.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> list = imageService.getImages();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/flower/{flowerId}")
    public ResponseEntity<List<Image>> getImagesByFlowerId(@PathVariable int flowerId) {
        List<Image> list = imageService.getImagesByFlowerId(flowerId);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<String> insertImage(@RequestBody Image image) {
        if (image.getId() == null) {
            image.setId(new ImageId(0));
        }
        if (image.getFlowerId() == null) {
            return ResponseEntity.badRequest().body("Flower ID is required for image.");
        }
        boolean result = imageService.insertImage(image);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Image inserted successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to insert image.");
    }

    @PutMapping
    public ResponseEntity<String> updateImage(@RequestBody Image image) {
        if (image.getId() == null) {
            return ResponseEntity.badRequest().body("Image ID is required for update.");
        }
        boolean result = imageService.updateImage(image);
        if (result) {
            return ResponseEntity.ok("Image updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found or update failed.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable int id) {
        boolean result = imageService.deleteImage(id);
        if (result) {
            return ResponseEntity.ok("Image deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found or delete failed.");
    }
}
