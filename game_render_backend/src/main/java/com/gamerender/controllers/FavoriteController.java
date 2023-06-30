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
import com.gamerender.models.Favorite;
import com.gamerender.models.Image;
import com.gamerender.models.User;
import com.gamerender.service.CollectionService;
import com.gamerender.service.FavoriteService;
import com.gamerender.service.ImageService;
import com.gamerender.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(maxAge = 30)
@RestController
@RequestMapping("/favorites")
public class FavoriteController {

	@Autowired FavoriteService favoriteService;
	@Autowired UserService userService;
	@Autowired ImageService imageService;
	@Autowired CollectionService collectionService;

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
    public ResponseEntity<Favorite> createFavorite(@RequestBody Favorite favorite) {
        User user = userService.findUserById(favorite.getUser().getId());
        Image image = imageService.findImageById(favorite.getImage().getImageID());
        Collection collection = collectionService.findCollectionById(favorite.getCollection().getCollectionID());

        favorite.setUser(user);
        favorite.setImage(image);
        favorite.setCollection(collection);

        Favorite createdFavorite = favoriteService.createFavorite(favorite);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFavorite);
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
