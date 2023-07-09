package com.gamerender.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamerender.exceptions.CategoryNotFoundException;
import com.gamerender.models.Category;
import com.gamerender.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/categories")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch(Exception e) {
            throw new CategoryNotFoundException("Categories not found");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch(Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        try {
            categoryService.createCategory(category.getCategoryName());
            Category retrievedCategory = categoryService.getCategoryByCategoryName(category.getCategoryName());
            return new ResponseEntity<>(retrievedCategory, HttpStatus.CREATED);
        } catch(Exception e) {
            throw new CategoryNotFoundException("Category could not be created. " + e.getMessage());
        }
    }
//    
//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Category> createCategory(@Valid @RequestParam String categoryName) {
//        try {
//        	Category createdCat = categoryService.createCategory(new Category(categoryName));
//            return new ResponseEntity<>(createdCat, HttpStatus.CREATED);
//        } catch(Exception e) {
//            throw new CategoryNotFoundException("Category could not be created!");
//        }
//    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
        try {
            category.setCategoryID(id);
            Category updatedCategory = categoryService.updateCategory(category);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch(Exception e) {
            throw new CategoryNotFoundException("Category could not be updated");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e) {
            throw new CategoryNotFoundException("Category not found, so could not be deleted");
        }
    }
}