package com.gamerender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamerender.models.Favorite;
import com.gamerender.models.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>{
	List<Favorite> findFavoritesByUser(User user);
}
