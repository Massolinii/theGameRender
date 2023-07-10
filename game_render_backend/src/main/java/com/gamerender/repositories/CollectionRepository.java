package com.gamerender.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gamerender.models.Category;
import com.gamerender.models.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
	
	boolean existsByCollectionName(String collectionName);
	
    List<Collection> findCollectionsByCategory(Category category);

    @Query("SELECT c FROM Collection c WHERE c.category.categoryID = :categoryId")
    List<Collection> findCollectionsByCategory(@Param("categoryId") Long categoryId);

	
}
