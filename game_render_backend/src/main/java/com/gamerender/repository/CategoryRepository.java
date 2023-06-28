package com.gamerender.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gamerender.models.Category;
import com.gamerender.models.Image;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findByCategoryName(String categoryName);
	
	@Query("SELECT c.images FROM Category c WHERE c.categoryID = :categoryId")
    List<Image> findImagesByCategoryId(@Param("categoryId") Long categoryId);
}
