package com.gamerender.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamerender.models.Category;
import com.gamerender.models.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
	
	boolean existsByCollectionName(String collectionName);
	
    List<Collection> findCollectionsByCategory(Category category);
	
}
