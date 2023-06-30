package com.gamerender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamerender.exceptions.CollectionNotFoundException;
import com.gamerender.models.Category;
import com.gamerender.models.Collection;
import com.gamerender.repository.CollectionRepository;

@Service
public class CollectionService {

    @Autowired private CollectionRepository collectionRepository;
    @Autowired private CategoryService categoryService;

    public Collection createCollection(Collection collection) {
        Category category = categoryService.findCategoryById(collection.getCategory().getCategoryID());
        collection.setCategory(category);
        return collectionRepository.save(collection);
    }

    public Collection findCollectionById(Long id) {
        return collectionRepository.findById(id).orElseThrow(() -> new CollectionNotFoundException("Collection with ID " + id + " not found."));
    }

    public List<Collection> findAllCollections() {
        return collectionRepository.findAll();
    }

    public Collection updateCollection(Collection collection) {
        if (!collectionRepository.existsById(collection.getCollectionID())) {
            throw new CollectionNotFoundException("Collection with ID " + collection.getCollectionID() + " not found.");
        }
        return collectionRepository.save(collection);
    }

    public void deleteCollection(Long id) {
        if (!collectionRepository.existsById(id)) {
            throw new CollectionNotFoundException("Collection with ID " + id + " not found.");
        }
        collectionRepository.deleteById(id);
    }
    
    public List<Collection> findCollectionsByCategory(Long categoryId) {
        Category category = categoryService.findCategoryById(categoryId);
        return collectionRepository.findCollectionsByCategory(category);
    }
}