package com.gamerender.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamerender.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
