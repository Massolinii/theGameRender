package com.gamerender.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gamerender.models.Collection;
import com.gamerender.models.Image;
import com.gamerender.repositories.ImageRepository;
import com.gamerender.service.CollectionService;
import com.gamerender.service.ImageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/images")
public class ImageController {

	@Autowired ImageService imageService;
	@Autowired ImageRepository imageRepository;
	@Autowired CollectionService collectionService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.getAllImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id) {
        Optional<Image> dbImage = imageRepository.findById(id);
        if (dbImage.isPresent()) {
            Image image = dbImage.get();
            Map<String, String> response = new HashMap<>();
            response.put("url", image.getUrl());
            response.put("promptText", image.getPromptText());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/tags/{tag}")
    public ResponseEntity<List<Image>> getImagesByTag(@PathVariable String tag) {
        List<Image> images = imageService.getImagesByTag(tag);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createImage(@RequestParam("image") MultipartFile imageFile, 
                                         @RequestParam("prompt") String prompt, 
                                         @RequestParam("collectionId") Long collectionId,
                                         @RequestParam("tags") Set<String> tags) throws IOException {
        Collection collection = collectionService.getCollectionById(collectionId);
        Image createdImage = imageService.createImage(imageFile, prompt, collection, tags);
        return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Image> updateImage(
    		@PathVariable Long id, 
    		@Valid @RequestBody Image image) {
        image.setImageID(id);
        Image updatedImage = imageService.updateImage(image);
        return new ResponseEntity<>(updatedImage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
