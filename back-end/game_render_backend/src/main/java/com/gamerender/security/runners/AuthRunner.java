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
import com.gamerender.security.models.SecUser;
import com.gamerender.security.repositories.RoleRepository;
import com.gamerender.security.repositories.SecUserRepository;
import com.gamerender.security.services.AuthService;

@Component
public class AuthRunner implements ApplicationRunner {

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	SecUserRepository secUserRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthService authService;

	private Set<Role> adminRole;
	private Set<Role> userRole;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Authentication is running...");
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
		if (secUserRepository.findByEmail("andrea.ragalzi@epicode.com").isEmpty()) {
			SecUser admin = new SecUser();
			admin.setUsername("andrea.ragalzi");
			admin.setEmail("andrea.ragalzi@epicode.com");
			admin.setPassword(passwordEncoder.encode("root"));
			admin.setFirstname("Andrea");
			admin.setLastname("Ragalzi");
			admin.setRoles(adminRole);
			secUserRepository.save(admin);
		}

		if (secUserRepository.findByEmail("lucy.parsons@epicode.com").isEmpty()) {
			SecUser secUser = new SecUser();
			secUser.setUsername("lucy.parsons");
			secUser.setEmail("lucy.parsons@epicode.com");
			secUser.setPassword(passwordEncoder.encode("user"));
			secUser.setFirstname("Lucy");
			secUser.setLastname("Parsons");
			secUser.setRoles(userRole);
			secUserRepository.save(secUser);
		}

	}

}
