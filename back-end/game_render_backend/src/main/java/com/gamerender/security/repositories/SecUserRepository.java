package com.gamerender.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamerender.security.models.SecUser;

import java.util.Optional;

public interface SecUserRepository extends JpaRepository<SecUser, Long> {

    Optional<SecUser> findByEmail(String email);

    Optional<SecUser> findByUsernameOrEmail(String username, String email);

    Optional<SecUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
