package com.gamerender.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamerender.exceptions.CategoryNotFoundException;
import com.gamerender.exceptions.CollectionAlreadyExistsException;
import com.gamerender.exceptions.CollectionNotFoundException;
import com.gamerender.models.Category;
import com.gamerender.models.Collection;
import com.gamerender.repositories.CollectionRepository;

@Service
public class CollectionService {
	
	@Autowired
	private CollectionRepository collectionRepository;
	@Autowired
	private CategoryService categoryService;
	
	// GET
    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }
    
	public Collection getCollectionById(Long id) {
        Optional<Collection> collection = collectionRepository.findById(id);
        if (collection.isPresent()) {
            return collection.get();
        } else {
            throw new CollectionNotFoundException("Collection not found with ID: " + id);
        }
    }
	
    public List<Collection> getCollectionsByCategory(Category category) {
        return collectionRepository.findCollectionsByCategory(category);
    }
    
    public boolean existsById(Long id) {
        return collectionRepository.existsById(id);
    }
	

 // POST
    public Collection createCollection(Collection collection) {
        if (collectionRepository.existsById(collection.getCollectionID())) {
            throw new CollectionAlreadyExistsException("Collection already exists with ID: " + collection.getCollectionID());
        }
        validateCategory(collection.getCategory());
        return collectionRepository.save(collection);
    }


    // PUT
    public Collection updateCollection(Collection collection) {
        if (collectionRepository.existsById(collection.getCollectionID())) {
        	validateCategory(collection.getCategory());
        	return collectionRepository.save(collection);
        } else {
            throw new CollectionNotFoundException("Collection not found with ID: " + collection.getCollectionID());
        }
    }

    // DELETE
    public String deleteCollection(Long id) {
        if (collectionRepository.existsById(id)) {
        	collectionRepository.deleteById(id);
        	return "Collection eliminated.";
        } else {
            throw new CollectionNotFoundException("Collection not found with ID: " + id);
        }       
    }

    private void validateCategory(Category category) {
        if (category == null || !categoryService.existsById(category.getCategoryID())) {
            throw new CategoryNotFoundException("Invalid category.");
        }
    }
}
