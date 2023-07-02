package com.gamerender.configurations;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.gamerender.models.Image;
import com.gamerender.repositories.ImageRepository;


@Configuration
public class CloudinaryConfig {
    
    private String cloudName = "dfqlb8zrq";
    private String apiKey = "259265923815555";
    private String apiSecret = "tmPyKLsLXpYVOeHXS-hIdVA6i2M";
    
    private Cloudinary cloudinary;

    @Autowired
    private ImageRepository imageRepository;

    public void uploadImages() throws IOException {
        try {
            File file = new File("./images/bg_stonetower.jpg");
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            
            String imageUrl = (String) uploadResult.get("url");
            System.out.println(imageUrl);

            Image image = new Image();
            image.setUrl(imageUrl);
            image.setPromptText("This is a prompt text");

            imageRepository.save(image);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("lol no");
        }
    }
    
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
        		"cloud_name", cloudName,
        		"api_key", apiKey,
        		"api_secret", apiSecret));
    }
}

