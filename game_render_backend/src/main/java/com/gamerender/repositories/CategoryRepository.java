package com.gamerender.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamerender.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Optional<Category> findByCategoryName(String categoryName);
	
}
