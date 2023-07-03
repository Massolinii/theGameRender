package com.gamerender.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.gamerender.exceptions.ImageAlreadyExistsException;
import com.gamerender.exceptions.ImageNotFoundException;
import com.gamerender.models.Image;
import com.gamerender.models.Tag;
import com.gamerender.repositories.ImageRepository;
import com.gamerender.repositories.TagRepository;

@Service
public class ImageService {
	
	@Autowired ImageRepository imageRepository;
	@Autowired TagRepository tagRepository;
	
	Cloudinary cloudinary;
	
    
	public Image createImage(MultipartFile imageFile, String prompt, List<String> tags) throws IOException {
	    Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
	    
	    Image image = new Image();
	    image.setTitle(imageFile.getOriginalFilename());
	    image.setUrl(uploadResult.get("url").toString());
	    image.setPromptText(prompt);
	    
	    List<Tag> imageTags = new ArrayList<Tag>();
	    for (String tagName : tags) {
	        // Retrieve the tag from the database if it exists, otherwise create a new one
	        Tag tag = tagRepository.findByTagName(tagName).orElse(new Tag(tagName));
	        imageTags.add(tag);
	    }
	    image.setTags(imageTags);
	    
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

    public String deleteImage(Long id) {
    	if (!imageRepository.existsById(id)) {
            throw new ImageNotFoundException("Image with ID " + id + " not found.");
        }
    	imageRepository.deleteById(id);
        return "Image removed";
    }
    
    public String uploadImage(MultipartFile imageFile) throws IOException {
        @SuppressWarnings("rawtypes")
		Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
        
        var imageToSave = new Image();
        imageToSave.setTitle(imageFile.getOriginalFilename());
        imageToSave.setUrl(uploadResult.get("url").toString());
        return (String) uploadResult.get("url");
    }
    
    public List<Tag> getImageTags(Long id) {
        Image image = findImageById(id);
        return image.getTags();
    }
    
    
    
	
}