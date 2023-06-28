package com.gamerender.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gamerender.models.Category;
import com.gamerender.models.Image;
import com.gamerender.models.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	@Query("SELECT t FROM Tag t JOIN t.images i WHERE i.id = :imageId")
	List<Tag> findTagsByImageId(@Param("imageId") Long imageId);
    
    Optional<Category> findByTagName(String tagName);
    
    @Query("SELECT t.images FROM Tag t WHERE t.tagID = :tagId")
    List<Image> findImagesByTagId(@Param("tagId") Long tagId);
}
