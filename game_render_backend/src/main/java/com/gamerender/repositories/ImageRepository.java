package com.gamerender.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamerender.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	Optional<Image> findByUrl(String url);
}
