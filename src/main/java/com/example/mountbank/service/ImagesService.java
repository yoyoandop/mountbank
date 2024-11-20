package com.example.mountbank.service;

import com.example.mountbank.model.Images;
import com.example.mountbank.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagesService {

    @Autowired
    private ImagesRepository imagesRepository;

    // Create or Update an Image
    public Images saveImage(Images image) {
        return imagesRepository.save(image);
    }

    // Read all images
    public List<Images> getAllImages() {
        return imagesRepository.findAll();
    }

    // Read image by ID
    public Optional<Images> getImageById(Long id) {
        return imagesRepository.findById(id);
    }

    // Read image by book name
    public Images getImageByBookName(String bookName) {
        return imagesRepository.findByBookName(bookName);
    }

    // Delete an image
    public void deleteImage(Long id) {
        imagesRepository.deleteById(id);
    }
}
