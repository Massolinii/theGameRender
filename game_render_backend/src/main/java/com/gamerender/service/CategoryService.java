package com.gamerender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamerender.exceptions.CategoryAlreadyExistsException;
import com.gamerender.exceptions.CategoryNotFoundException;
import com.gamerender.models.Category;
import com.gamerender.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired private CategoryRepository categoryRepository;

	public Category createCategory(Category category) {
        if (categoryRepository.findByCategoryName(category.getCategoryName()).isPresent()) {
            throw new CategoryAlreadyExistsException("Category with name " + category.getCategoryName() + " already exists.");
        }
        return categoryRepository.save(category);
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found."));
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(Category category) {
        if (!categoryRepository.existsById(category.getCategoryID())) {
            throw new CategoryNotFoundException("Category with ID " + category.getCategoryID() + " not found.");
        }
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category with ID " + id + " not found.");
        }
        categoryRepository.deleteById(id);
    }
    
}
