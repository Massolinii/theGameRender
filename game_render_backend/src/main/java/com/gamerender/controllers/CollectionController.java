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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gamerender.exceptions.CategoryNotFoundException;
import com.gamerender.exceptions.CollectionNotFoundException;
import com.gamerender.models.Category;
import com.gamerender.models.Collection;
import com.gamerender.service.CategoryService;
import com.gamerender.service.CollectionService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/collections")
public class CollectionController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired CollectionService collectionService;
    @Autowired CategoryService categoryService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Collection>> getAllCollections() {
        try {
            List<Collection> collections = collectionService.getAllCollections();
            return new ResponseEntity<>(collections, HttpStatus.OK);
        } catch(Exception e) {
            throw new CollectionNotFoundException(HttpStatus.NOT_FOUND, "Collections not found");
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Collection> getCollectionById(@PathVariable Long id) {
        try {
            Collection collection = collectionService.getCollectionById(id);
            return new ResponseEntity<>(collection, HttpStatus.OK);
        } catch(Exception e) {
            throw new CollectionNotFoundException(HttpStatus.NOT_FOUND, "Collection not found");
        }
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<List<Collection>> getCollectionsByCategory(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            if (category == null) {
                throw new CategoryNotFoundException(HttpStatus.NOT_FOUND, "Category not found");
            }
            List<Collection> collections = collectionService.getCollectionsByCategory(category);
            return new ResponseEntity<>(collections, HttpStatus.OK);
        } catch(Exception e) {
            throw new CategoryNotFoundException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection> createCollection(@Valid @RequestBody Collection collection) {
        try {
            Category category = categoryService.getCategoryById(collection.getCategory().getCategoryID());
            if (category == null) {
                throw new CategoryNotFoundException(HttpStatus.NOT_FOUND, "Category not found");
            }
            collection.setCategory(category);
            Collection createdCollection = collectionService.createCollection(collection.getCollectionName(), collection.getCategory().getCategoryID());
            return new ResponseEntity<>(createdCollection, HttpStatus.CREATED);
        } catch(Exception e) {
            throw new CollectionNotFoundException("Collection could not be created. " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection> updateCollection(@PathVariable Long id, @Valid @RequestBody Collection collection) {
        try {
            collection.setCollectionID(id);
            Collection updatedCollection = collectionService.updateCollection(collection);
            return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
        } catch(Exception e) {
            throw new CollectionNotFoundException(HttpStatus.BAD_REQUEST, "Collection could not be updated");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        try {
            collectionService.deleteCollection(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e) {
            throw new CollectionNotFoundException(HttpStatus.NOT_FOUND, "Collection not found, so could not be deleted");
        }
    }
}
