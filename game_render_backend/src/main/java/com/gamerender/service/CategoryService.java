package com.gamerender.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamerender.exceptions.CategoryNotFoundException;
import com.gamerender.models.Category;
import com.gamerender.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired private CategoryRepository categoryRepository;
	
	// GET
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
        
    }
    
	public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new CategoryNotFoundException(
                     "Category not found with ID: " + id);
        }
    }

	// POST
    public Category createCategory(Category category) {
    	return categoryRepository.save(category);
    }

    // PUT
    public Category updateCategory(Category category) {
        if (categoryRepository.existsById(category.getCategoryID())) {
        	return categoryRepository.save(category);
        } else {
            throw new CategoryNotFoundException(
                    "Category not found with ID: " + category.getCategoryID());
        }
    }

    // DELETE
    public String deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
        	categoryRepository.deleteById(id);
        	return ("Category eliminated.");
        } else {
            throw new CategoryNotFoundException(
                    "Category not found with ID: " + id);
        }       
    }

    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }
}
