package com.gamerender.security.runners;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gamerender.security.enumerates.UserRole;
import com.gamerender.security.models.Role;
import com.gamerender.security.repositories.RoleRepository;
import com.gamerender.models.User;
import com.gamerender.repositories.UserRepository;
import com.gamerender.security.services.AuthService;

@Component
public class AuthRunner implements ApplicationRunner {

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthService authService;

	private Set<Role> adminRole;
	private Set<Role> userRole;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Run...");
		setRoleDefault();
		saveUserDefault();
	}

	private void setRoleDefault() {
		Role admin = new Role();
		admin.setRole(UserRole.ROLE_ADMIN);

		Role user = new Role();
		user.setRole(UserRole.ROLE_USER);

		if (!roleRepository.existsByRole(UserRole.ROLE_ADMIN)) {
			roleRepository.save(admin);
		}

		if (!roleRepository.existsByRole(UserRole.ROLE_USER)) {
			roleRepository.save(user);
		}

		adminRole = new HashSet<Role>();
		adminRole.add(admin);
		adminRole.add(user);

		userRole = new HashSet<Role>();
		userRole.add(user);
	}

	public void saveUserDefault() {
		if (userRepository.findByEmail("masso.lini@epicode.com").isEmpty()) {
			User admin = new User();
			admin.setUsername("masso.lini");
			admin.setEmail("masso.lini@epicode.com");
			admin.setPassword(passwordEncoder.encode("root"));
			admin.setFirstname("Massimiliano");
			admin.setLastname("Esposito");
			admin.setRoles(adminRole);
			userRepository.save(admin);
		}

		if (userRepository.findByEmail("lucy.parsons@epicode.com").isEmpty()) {
			User user = new User();
			user.setUsername("lucy.parsons");
			user.setEmail("lucy.parsons@epicode.com");
			user.setPassword(passwordEncoder.encode("user"));
			user.setFirstname("Lucy");
			user.setLastname("Parsons");
			user.setRoles(userRole);
			userRepository.save(user);
		}

	}

}
