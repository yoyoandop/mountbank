package com.example.mountbank.controller;

import com.example.mountbank.model.Images;
import com.example.mountbank.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImagesController {

    @Autowired
    private ImagesService imagesService;

    // Create or Update an image
    @PostMapping
    public ResponseEntity<Images> saveImage(@RequestBody Images image) {
        Images savedImage = imagesService.saveImage(image);
        return ResponseEntity.ok(savedImage);
    }

    // Get all images
    @GetMapping
    public ResponseEntity<List<Images>> getAllImages() {
        List<Images> images = imagesService.getAllImages();
        return ResponseEntity.ok(images);
    }

    // Get image by ID
    @GetMapping("/{id}")
    public ResponseEntity<Images> getImageById(@PathVariable Long id) {
        Optional<Images> image = imagesService.getImageById(id);
        return image.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get image by book name
    @GetMapping("/book/{bookName}")
    public ResponseEntity<Images> getImageByBookName(@PathVariable String bookName) {
        Images image = imagesService.getImageByBookName(bookName);
        return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
    }

    // Delete image
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imagesService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
