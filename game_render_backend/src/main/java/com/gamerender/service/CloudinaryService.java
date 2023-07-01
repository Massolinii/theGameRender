package com.gamerender.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.annotation.PostConstruct;

@Service
public class CloudinaryService {

    private Cloudinary cloudinary;

    @PostConstruct
    public void init() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dfqlb8zrq",
            "api_key", "259265923815555",
            "api_secret", "tmPyKLsLXpYVOeHXS-hIdVA6i2M"));
    }

    @SuppressWarnings("rawtypes")
	public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            throw new RuntimeException("Could not upload file", e);
        }
    }
}
