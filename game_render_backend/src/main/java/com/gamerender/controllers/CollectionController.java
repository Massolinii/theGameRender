package com.gamerender.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamerender.models.Collection;
import com.gamerender.service.CollectionService;

import jakarta.validation.Valid;

@CrossOrigin(maxAge = 30)
@RestController
@RequestMapping("/collections")
public class CollectionController {

    @Autowired CollectionService collectionService;

    @GetMapping("/{id}")
    public ResponseEntity<Collection> getCollection(@PathVariable Long id) {
        Collection collection = collectionService.findCollectionById(id);
        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Collection>> getAllCollections() {
        List<Collection> collections = collectionService.findAllCollections();
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Collection> createCollection(@Valid @RequestBody Collection collection) {
        Collection createdCollection = collectionService.createCollection(collection);
        return new ResponseEntity<>(createdCollection, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Collection> updateCollection(@PathVariable Long id, @Valid @RequestBody Collection collection) {
        collection.setCollectionID(id);
        Collection updatedCollection = collectionService.updateCollection(collection);
        return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // GET ALL collections FROM category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Collection>> getCollectionsByCategory(@PathVariable Long categoryId) {
        List<Collection> collections = collectionService.findCollectionsByCategory(categoryId);
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }
}