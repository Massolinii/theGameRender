package com.gamerender.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamerender.models.Image;
import com.gamerender.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
    List<User> findUsersByFavoriteImages(Image image);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}