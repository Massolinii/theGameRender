package com.gamerender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamerender.exceptions.FavoriteNotFoundException;
import com.gamerender.models.Favorite;
import com.gamerender.models.User;
import com.gamerender.repository.FavoriteRepository;

@Service
public class FavoriteService {

    @Autowired private FavoriteRepository favoriteRepository;
    @Autowired private UserService userService;

    public Favorite createFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public Favorite findFavoriteById(Long id) {
        return favoriteRepository.findById(id).orElseThrow(() -> new FavoriteNotFoundException("Favorite with ID " + id + " not found."));
    }

    public List<Favorite> findAllFavorites() {
        return favoriteRepository.findAll();
    }

    public Favorite updateFavorite(Favorite favorite) {
        if (!favoriteRepository.existsById(favorite.getFavoriteID())) {
            throw new FavoriteNotFoundException("Favorite with ID " + favorite.getFavoriteID() + " not found.");
        }
        return favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Long id) {
        if (!favoriteRepository.existsById(id)) {
            throw new FavoriteNotFoundException("Favorite with ID " + id + " not found.");
        }
        favoriteRepository.deleteById(id);
    }
    
    public List<Favorite> findFavoritesByUser(Long userId) {
    	User user = userService.findUserById(userId);
        return favoriteRepository.findFavoritesByUser(user);
    }
}
