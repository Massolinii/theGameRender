package com.gamerender.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.gamerender.exceptions.ImageNotFoundException;
import com.gamerender.exceptions.ImageUploadException;
import com.gamerender.models.Collection;
import com.gamerender.models.Image;
import com.gamerender.repositories.CollectionRepository;
import com.gamerender.repositories.ImageRepository;

@Service
public class ImageService {
	
	@Autowired
	private Cloudinary cloudinary;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private CollectionRepository collectionRepository;
	
	// GET
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
    
	public Image getImageById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            return image.get();
        } else {
            throw new ImageNotFoundException("Image not found withID: " + id);
        }
    }
	
    public List<Image> getImagesByCollection(Collection collection) {
        return imageRepository.findImagesByCollection(collection);
    }
    
    public List<Image> getImagesByCategory(Long categoryId) {
        List<Collection> collections = collectionRepository.findCollectionsByCategory(categoryId);
        List<Image> images = new ArrayList<>();

        for (Collection collection : collections) {
            images.addAll(getImagesByCollection(collection));
        }

        return images;
    }
    
    public List<Image> getImagesByTags(String tag) {
        return imageRepository.findImagesByTags(tag);
            
    }
    
    public List<String> getAllUniqueTags() {
        return imageRepository.findAllUniqueTags();
    }
    
    public List<Image> getImagesByKeyword(String keyword) {
        return imageRepository.findImagesByKeyword(keyword);
    }

	// POST
    public Image createImage(MultipartFile imageFile, String prompt, Collection collection, Set<String> tags) {
        validateCollection(collection);
        @SuppressWarnings("rawtypes")
		Map uploadResult = null;
        
        try {     
            Image newImage = new Image();
              
            newImage.setPromptText(prompt);
            newImage.setTags(new HashSet<String>(tags));
            newImage.setCollection(collection);
            
            uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.asMap(
                    "resource_type", "auto",
                    "quality", "auto"
                ));
            String imageUrl = (String) uploadResult.get("url");
            newImage.setUrl(imageUrl);
            
            return imageRepository.save(newImage);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImageUploadException("Failed to upload image");
        }
        
    }

 // PUT
    public Image updateImage(Image image) {
        if (imageRepository.existsById(image.getImageID())) {
        	validateCollection(image.getCollection());
        	return imageRepository.save(image);
        } else {
            throw new ImageNotFoundException("Image not found with ID: " + image.getImageID());
        }
    }

    // DELETE
    public String deleteImage(Long id) {
        if (imageRepository.existsById(id)) {
        	imageRepository.deleteById(id);
        	return "Image eliminated.";
        } else {
            throw new ImageNotFoundException("Image not found with ID: " + id);
        }       
    }

    private void validateCollection(Collection collection) {
        if (collection == null || !collectionService.existsById(collection.getCollectionID())) {
            throw new ImageNotFoundException("Invalid collection.");
        }
    }
}