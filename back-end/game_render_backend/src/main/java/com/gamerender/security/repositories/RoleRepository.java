package com.gamerender.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamerender.security.enumerates.UserRole;
import com.gamerender.security.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
	Optional<Role> findByRole(UserRole role);

	Boolean existsByRole(UserRole role);

}
