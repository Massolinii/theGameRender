package com.gamerender.service;

import java.io.IOException;
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
import com.gamerender.repositories.ImageRepository;

@Service
public class ImageService {
	
	@Autowired private Cloudinary cloudinary;
	@Autowired private ImageRepository imageRepository;
	
	// GET
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
    
	public Image getImageById(Long id) {
        Optional<Image> Image = imageRepository.findById(id);
        if (Image.isPresent()) {
            return Image.get();
        } else {
            throw new ImageNotFoundException(
                    "Image not found with ID: " + id);
        }
    }
	
    public List<Image> getImagesByCategory(Collection collection) {
        return imageRepository.findImagesByCollection(collection);
    }
    
    public List<Image> getImagesByTag(String tag) {
        return imageRepository.findImagesByTag(tag);
    }
	

	// POST
    public Image createImage(MultipartFile imageFile, String prompt, Collection collection, Set<String> tags) {
        @SuppressWarnings("rawtypes")
		Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImageUploadException("Failed to upload image");
        }
        String imageUrl = (String) uploadResult.get("url");

        Image newImage = new Image();
        newImage.setUrl(imageUrl);
        newImage.setPromptText(prompt);
        newImage.setTags(new HashSet<String>(tags));
        newImage.setCollection(collection);

        return imageRepository.save(newImage);
    }

    // PUT
    public Image updateImage(Image Image) {
        if (imageRepository.existsById(Image.getImageID())) {
        	return imageRepository.save(Image);
        } else {
            throw new ImageNotFoundException(
                    "Image not found with ID: " + Image.getImageID());
        }
    }

    // DELETE
    public String deleteImage(Long id) {
        if (imageRepository.existsById(id)) {
        	imageRepository.deleteById(id);
        	return ("Image eliminated.");
        } else {
            throw new ImageNotFoundException(
                    "Image not found with ID: " + id);
        }       
    }
}
