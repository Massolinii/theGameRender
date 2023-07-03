package com.gamerender.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.gamerender.models.Image;
import com.gamerender.models.Tag;
import com.gamerender.repositories.ImageRepository;
import com.gamerender.service.ImageService;

import jakarta.validation.Valid;

@CrossOrigin(maxAge = 30)
@RestController
@RequestMapping("/images")
public class ImageController {

	@Autowired ImageService imageService;
	@Autowired ImageRepository imageRepository;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.findAllImages();
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
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createImage(@RequestParam("image") MultipartFile imageFile, 
    									 @RequestParam("prompt") String prompt, 
    									 @RequestParam("tags") List<String> tags) throws IOException {
        Image createdImage = imageService.createImage(imageFile, prompt, tags);
        return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Image> updateImage(@PathVariable Long id, @Valid @RequestBody Image image) {
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
    
    @GetMapping("/{id}/tags")
    public ResponseEntity<?> getImageTags(@PathVariable Long id) {
        List<Tag> tags = imageService.getImageTags(id);
        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }
    
    @PostMapping("/uploadImage")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadImage(
    		@RequestParam("image") MultipartFile imageFile, 
            @RequestParam("promptText") String promptText,
            @RequestParam("collectionId") Long collectionId,
            @RequestParam("tags") List<Tag> tags) throws IOException {
        String imageUrl = imageService.uploadImage(imageFile, promptText, collectionId, tags);
        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }

}
