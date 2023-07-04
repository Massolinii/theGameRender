package com.gamerender.runner;

import com.gamerender.models.*;
import com.gamerender.repositories.*;
import com.gamerender.security.enumerates.UserRole;
import com.gamerender.security.models.Role;
import com.gamerender.security.repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Component
public class AppRunner implements ApplicationRunner {

	@Autowired UserRepository userRepository;
	@Autowired ImageRepository imageRepository;
	@Autowired CollectionRepository collectionRepository;
	@Autowired CategoryRepository categoryRepository;
	@Autowired RoleRepository roleRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
	    // Check if the control email is already present
	    Optional<User> existingControlUser = userRepository.findByEmail("safetytest@impossiblemail.com");
	    if (existingControlUser.isPresent()) {
	        System.out.println("Control email found - No database initialization.");
	        return;
	    }

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

	    // Create some users
	    User user1 = new User("user1", "safetytest@impossiblemail.com", "password1");
	    User user2 = new User("user2", "user2@gmail.com", "password2");
	    User user3 = new User("user3", "user3@gmail.com", "password3");

	    // Create roles
	    Role adminRole = new Role();
	    adminRole.setRole(UserRole.ROLE_ADMIN);

	    Role userRole = new Role();
	    userRole.setRole(UserRole.ROLE_USER);

	    roleRepository.saveAll(Arrays.asList(adminRole, userRole));

	    // Assign role to users
	    user1.setRoles(new HashSet<>(Arrays.asList(userRole)));
	    user2.setRoles(new HashSet<>(Arrays.asList(userRole)));
	    user3.setRoles(new HashSet<>(Arrays.asList(userRole)));

	    // Create an admin user
	    User admin = new User("admin", "admin@gmail.com", "adminpassword");
	    admin.setRoles(new HashSet<>(Arrays.asList(adminRole)));
	    userRepository.saveAll(Arrays.asList(user1, user2, user3, admin));

	    // Add favorite images to user's collections
	    user1.setFavoriteImages(new HashSet<>(Arrays.asList(image1, image2)));
	    user2.setFavoriteImages(new HashSet<>(Arrays.asList(image2, image3)));
	    user3.setFavoriteImages(new HashSet<>(Arrays.asList(image1, image3)));
	    userRepository.saveAll(Arrays.asList(user1, user2, user3));

	    // Log a console
	    System.out.println("Database successfully initialized");
	}
}

