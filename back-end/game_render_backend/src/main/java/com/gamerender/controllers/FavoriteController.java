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

import com.gamerender.models.Favorite;
import com.gamerender.service.FavoriteService;

import jakarta.validation.Valid;

@CrossOrigin(maxAge = 30)
@RestController
@RequestMapping("/favorites")
public class FavoriteController {

	@Autowired private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Favorite> getFavorite(@PathVariable Long id) {
        Favorite favorite = favoriteService.findFavoriteById(id);
        return new ResponseEntity<>(favorite, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Favorite>> getAllFavorites() {
        List<Favorite> favorites = favoriteService.findAllFavorites();
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Favorite> createFavorite(@Valid @RequestBody Favorite favorite) {
        Favorite createdFavorite = favoriteService.createFavorite(favorite);
        return new ResponseEntity<>(createdFavorite, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Favorite> updateFavorite(@PathVariable Long id, @Valid @RequestBody Favorite favorite) {
        favorite.setFavoriteID(id);
        Favorite updatedFavorite = favoriteService.updateFavorite(favorite);
        return new ResponseEntity<>(updatedFavorite, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        favoriteService.deleteFavorite(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // Get all favorites by user
    @GetMapping("/client/{userId}")
    public ResponseEntity<List<Favorite>> getFavoritesByUser(@PathVariable Long userId) {
        List<Favorite> favorites = favoriteService.findFavoritesByUser(userId);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }
}
