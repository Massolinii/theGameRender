package com.gamerender.controllers;

import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gamerender.exceptions.UserEmailAlreadyExistsException;
import com.gamerender.exceptions.UserNotFoundException;
import com.gamerender.models.Image;
import com.gamerender.models.User;
import com.gamerender.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(maxAge = 30)
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired UserService userService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        try {
            User user = userService.findUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found with id " + id);
        }
    }
    
    @GetMapping("/favorites/{id}")
    public ResponseEntity<Set<Image>> getUserFavorites(@PathVariable Long id) {
        Set<Image> favorites = userService.getUserFavoriteImages(id);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }
    
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new UserEmailAlreadyExistsException("Email already in use");
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    
    @PutMapping("/{userId}/toggleFavorite/{imageId}")
    public ResponseEntity<User> toggleFavorite(@PathVariable Long userId, @PathVariable Long imageId) {
        User updatedUser = userService.toggleFavoriteImage(userId, imageId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    
//    @PutMapping("/{userId}/addFavorite/{imageId}")
//    public ResponseEntity<User> addFavorite(@PathVariable Long userId, @PathVariable Long imageId) {
//        User updatedUser = userService.addFavoriteImage(userId, imageId);
//        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//    }
//
//    @PutMapping("/{userId}/removeFavorite/{imageId}")
//    public ResponseEntity<User> removeFavorite(@PathVariable Long userId, @PathVariable Long imageId) {
//        User updatedUser = userService.removeFavoriteImage(userId, imageId);
//        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
