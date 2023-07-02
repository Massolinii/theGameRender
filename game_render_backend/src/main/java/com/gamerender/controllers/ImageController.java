package com.gamerender.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gamerender.models.Image;
import com.gamerender.service.CloudinaryService;
import com.gamerender.service.ImageService;

import jakarta.validation.Valid;

@CrossOrigin(maxAge = 30)
@RestController
@RequestMapping("/images")
public class ImageController {

	@Autowired ImageService imageService;
	@Autowired CloudinaryService cloudinaryService;

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.findAllImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Image> getImage(@PathVariable Long id) {
        Image image = imageService.findImageById(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Image> createImage(@Valid @RequestBody Image image, MultipartFile imageFile) {
        Image createdImage = imageService.createImage(image, imageFile);
        return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable Long id, @Valid @RequestBody Image image) {
        image.setImageID(id);
        Image updatedImage = imageService.updateImage(image);
        return new ResponseEntity<>(updatedImage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/uploadImage")
    public ResponseEntity<Image> uploadImage(@RequestParam("imageFile") MultipartFile imageFile, @RequestParam("promptText") String promptText) {
        String imageUrl = cloudinaryService.uploadFile(imageFile);
        Image image = new Image();
        image.setTitle(imageFile.getOriginalFilename());
        image.setPromptText(promptText);
        image.setUrl(imageUrl);
        Image savedImage = imageService.createImage(image, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
    }

}
