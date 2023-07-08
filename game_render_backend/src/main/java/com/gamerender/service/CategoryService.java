package com.gamerender.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gamerender.exceptions.CategoryAlreadyExistsException;
import com.gamerender.exceptions.CategoryNotFoundException;
import com.gamerender.models.Category;
import com.gamerender.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	// GET
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
	public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new CategoryNotFoundException("Category not found with ID: " + id);
        }
    }
	
	public Category getCategoryByCategoryName(String categoryName) {
        
        if (categoryRepository.existsByCategoryName(categoryName)) {
        	Category category = categoryRepository.findByCategoryName(categoryName);
            return category;
        } else {
            throw new CategoryNotFoundException("Category not found with name: " + categoryName);
        }
    }

	// POST
	public Category createCategory(String categoryName) {
	    try {
	        if (categoryRepository.existsByCategoryName(categoryName)) {
	            throw new CategoryAlreadyExistsException("Category already exists with Name: " + categoryName);
	        } else {
	            Category category = new Category(categoryName);
	            return categoryRepository.save(category);
	        }   
	    } catch (DataIntegrityViolationException e) {
	        // This exception is thrown if there are database errors, like unique constraint violations.
	        throw new RuntimeException("Database error: " + e.getMessage());
	    } catch (Exception e) {
	        // This catches any other exceptions
	        throw new RuntimeException("Unexpected error: " + e.getMessage());
	    }
	}


    // PUT
    public Category updateCategory(Category category) {
        if (categoryRepository.existsById(category.getCategoryID())) {
        	return categoryRepository.save(category);
        } else {
            throw new CategoryNotFoundException("Category not found with ID: " + category.getCategoryID());
        }
    }

    // DELETE
    public String deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
        	categoryRepository.deleteById(id);
        	return "Category eliminated.";
        } else {
            throw new CategoryNotFoundException("Category not found with ID: " + id);
        }       
    }

    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }
}
