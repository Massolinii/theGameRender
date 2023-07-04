package com.gamerender.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gamerender.exceptions.CategoryNotFoundException;
import com.gamerender.models.Category;
import com.gamerender.models.Collection;
import com.gamerender.service.CategoryService;
import com.gamerender.service.CollectionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/collections")
public class CollectionController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired CollectionService collectionService;
    @Autowired CategoryService categoryService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Collection>> getAllCollections() {
        List<Collection> collections = collectionService.getAllCollections();
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Collection> getCollectionById(@PathVariable Long id) {
        Collection collection = collectionService.getCollectionById(id);
        return new ResponseEntity<>(collection, HttpStatus.OK);
    }
    
    // GET ALL collections FROM category
    @GetMapping("/categories/{id}")
    public ResponseEntity<List<Collection>> getCollectionsByCategory(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            throw new CategoryNotFoundException(HttpStatus.NOT_FOUND, "Category not found");
        }
        List<Collection> collections = collectionService.getCollectionsByCategory(category);
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }
    
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection> createCollection(@Valid @RequestBody Collection collection) {
        Collection createdCollection = collectionService.createCollection(collection);
        return new ResponseEntity<>(createdCollection, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection> updateCollection(@PathVariable Long id, @Valid @RequestBody Collection collection) {
        collection.setCollectionID(id);
        Collection updatedCollection = collectionService.updateCollection(collection);
        return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}