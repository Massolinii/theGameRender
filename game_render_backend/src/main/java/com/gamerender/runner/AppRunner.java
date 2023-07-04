package com.gamerender.runner;

import com.gamerender.models.*;
import com.gamerender.repositories.*;
import com.gamerender.security.enumerates.UserRole;
import com.gamerender.security.models.Role;
import com.gamerender.security.repositories.RoleRepository;
import com.gamerender.security.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class AppRunner implements ApplicationRunner {

	@Autowired UserRepository userRepository;
	@Autowired ImageRepository imageRepository;
	@Autowired CollectionRepository collectionRepository;
	@Autowired CategoryRepository categoryRepository;
	@Autowired RoleRepository roleRepository;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired AuthService authService;
	
	private Set<Role> adminRole;
	private Set<Role> userRole;
	
	User admin = new User();
	User user = new User();

	@Override
	public void run(ApplicationArguments args) throws Exception {
	    // Check if the control email is already present
	    Optional<User> existingControlUser = userRepository.findByEmail("safetytest@impossiblemail.com");
	    if (existingControlUser.isPresent()) {
	        System.out.println("Control email found - No database initialization.");
	        return;
	    } else {
	    	System.out.println("Run...");
			setRoleDefault();
			saveUserDefault();
			initializeDatabase();
	    }
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
			
			admin.setUsername("masso.lini");
			admin.setEmail("masso.lini@epicode.com");
			admin.setPassword(passwordEncoder.encode("root"));
			admin.setRoles(adminRole);
			userRepository.save(admin);
		}

		if (userRepository.findByEmail("lucy.parsons@epicode.com").isEmpty()) {
			
			user.setUsername("lucy.parsons");
			user.setEmail("lucy.parsons@epicode.com");
			user.setPassword(passwordEncoder.encode("user"));
			user.setRoles(userRole);
			userRepository.save(user);
		}
	}
	    
	public void initializeDatabase() {	
	    // Create some categories
	    Category category1 = new Category("Category 1");
	    Category category2 = new Category("Category 2");
	    Category category3 = new Category("Category 3");
	    categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
	   
	    // Create some collections
	    Collection collection1 = new Collection("Collection 1", category1);
	    Collection collection2 = new Collection("Collection 2", category2);
	    Collection collection3 = new Collection("Collection 3", category3);
	    collectionRepository.saveAll(Arrays.asList(collection1, collection2, collection3));
	
	
	    // Create some images with tags
	    Image image1 = new Image("url1", "prompt1", collection1, new HashSet<>(Arrays.asList("dark", "cozy")));
	    Image image2 = new Image("url2", "prompt2", collection2, new HashSet<>(Arrays.asList("light", "pixel")));
	    Image image3 = new Image("url3", "prompt3", collection3, new HashSet<>(Arrays.asList("realistic", "clay")));
	    imageRepository.saveAll(Arrays.asList(image1, image2, image3));


	    // Add favorite images to user's collections
	    admin.setFavoriteImages(new HashSet<>(Arrays.asList(image1, image2)));
	    user.setFavoriteImages(new HashSet<>(Arrays.asList(image2, image3)));
	    userRepository.saveAll(Arrays.asList(admin, user));
	    
	    // Log in console
	    System.out.println("Database successfully initialized");
	    
		}

}

