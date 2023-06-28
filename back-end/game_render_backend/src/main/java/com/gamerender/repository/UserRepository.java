package com.gamerender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamerender.models.Image;
import com.gamerender.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 List<User> findUsersByLikedImages(Image image);
}
