package com.gamerender.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamerender.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByCategoryName(String categoryName);
	
	Category findByCategoryName(String ccategoryName);
	
}
