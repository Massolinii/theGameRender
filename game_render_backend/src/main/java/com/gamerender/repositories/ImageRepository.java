package com.gamerender.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gamerender.models.Collection;
import com.gamerender.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	
	List<Image> findImagesByCollection(Collection collection);

	 @Query("SELECT i FROM Image i WHERE :tag MEMBER OF i.tags")
	List<Image> findImagesByTags(String tag);
	 
	 @Query("SELECT DISTINCT tag FROM Image i JOIN i.tags tag")
	    List<String> findAllUniqueTags();
}
