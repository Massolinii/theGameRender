package com.gamerender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gamerender.exceptions.ImageAlreadyExistsException;
import com.gamerender.exceptions.ImageNotFoundException;
import com.gamerender.models.Image;
import com.gamerender.repository.ImageRepository;

@Service
public class ImageService {
	
	@Autowired ImageRepository imageRepository;
	
	@Autowired CloudinaryService cloudinary;
    
	public Image createImage(Image image, MultipartFile imageFile) {
	    if (imageRepository.findById(image.getImageID()).isPresent()) {
	        throw new ImageAlreadyExistsException("Image with ID " + image.getImageID() + " already exists.");
	    }

	    String url = null;
	    try {
	        url = cloudinary.uploadFile(imageFile);
	    } catch (RuntimeException e) {
	        throw new RuntimeException("Could not upload image", e);
	    }

	    image.setUrl(url);

	    return imageRepository.save(image);
    }

    public Image findImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException("Image with ID " + id + " not found."));
    }

    public List<Image> findAllImages() {
        return imageRepository.findAll();
    }

    public Image updateImage(Image image) {
    	if (!imageRepository.existsById(image.getImageID())) {
            throw new ImageNotFoundException("Image with ID " + image.getImageID() + " not found.");
        }
        return imageRepository.save(image);
    }

    public void deleteImage(Long id) {
    	if (!imageRepository.existsById(id)) {
            throw new ImageNotFoundException("Image with ID " + id + " not found.");
        }
    	imageRepository.deleteById(id);
    }
    
	
}
