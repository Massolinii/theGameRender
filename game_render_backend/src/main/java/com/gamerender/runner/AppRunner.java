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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Run User Authentication...");
        setRoleDefault();
        saveUserDefault();
        initializeDatabase();
    }
    
    private void setRoleDefault() {
	    Role admin;
	    if (roleRepository.findByRole(UserRole.ROLE_ADMIN).isPresent()) {
	        admin = roleRepository.findByRole(UserRole.ROLE_ADMIN).get();
	    } else {
	        admin = new Role();
	        admin.setRole(UserRole.ROLE_ADMIN);
	        admin = roleRepository.save(admin);
	    }

	    Role user;
	    if (roleRepository.findByRole(UserRole.ROLE_USER).isPresent()) {
	        user = roleRepository.findByRole(UserRole.ROLE_USER).get();
	    } else {
	        user = new Role();
	        user.setRole(UserRole.ROLE_USER);
	        user = roleRepository.save(user);
	    }

	    adminRole = new HashSet<Role>();
	    adminRole.add(admin);
	    adminRole.add(user);

	    userRole = new HashSet<Role>();
	    userRole.add(user);
	}

    public void saveUserDefault() {
        saveUserIfNotExists("masso.lini@epicode.com", "masso.lini", "root", adminRole);
        saveUserIfNotExists("lucy.parsons@epicode.com", "lucy.parsons", "user", userRole);
    }

    private void saveUserIfNotExists(String email, String username, String password, Set<Role> roles) {
        if (userRepository.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
    
	public void initializeDatabase() {   
		
		 Category category1 = null;
		 Category category2 = null;
		 Category category3 = null;
		 Collection collection1 = null;
		 Collection collection2 = null;
		 Collection collection3 = null;
		 Image image1 = null;
		 Image image2 = null;
		 Image image3 = null;
		    
	    // Check if categories already exist
	    if (categoryRepository.count() > 0) {
	        System.out.println("Categories already exist - No need to initialize categories.");
	    } else {
	        // Create some categories
	         category1 = new Category("Category 1");
	         category2 = new Category("Category 2");
	         category3 = new Category("Category 3");
	        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
	    }
	    
	    // Check if collections already exist
	    if (collectionRepository.count() > 0) {
	        System.out.println("Collections already exist - No need to initialize collections.");
	    } else {
	        // Create some collections
	         collection1 = new Collection("Collection 1", category1);
	         collection2 = new Collection("Collection 2", category2);
	         collection3 = new Collection("Collection 3", category3);
	        collectionRepository.saveAll(Arrays.asList(collection1, collection2, collection3));
	    }
	    
	    // Check if images already exist
	    if (imageRepository.count() > 0) {
	        System.out.println("Images already exist - No need to initialize images.");
	    } else {
	        // Create some images with tags
	         image1 = new Image("url1", "prompt1", collection1, new HashSet<>(Arrays.asList("dark", "cozy")));
	         image2 = new Image("url2", "prompt2", collection2, new HashSet<>(Arrays.asList("light", "pixel")));
	         image3 = new Image("url3", "prompt3", collection3, new HashSet<>(Arrays.asList("realistic", "clay")));
	        imageRepository.saveAll(Arrays.asList(image1, image2, image3));
	    }

	 // Add favorite images to user's collections
        User admin = userRepository.findByEmail("masso.lini@epicode.com").orElseThrow(() -> new RuntimeException("Admin not found"));
        User user = userRepository.findByEmail("lucy.parsons@epicode.com").orElseThrow(() -> new RuntimeException("User not found"));
        admin.setFavoriteImages(new HashSet<>(Arrays.asList(image1, image2)));
        user.setFavoriteImages(new HashSet<>(Arrays.asList(image2, image3)));
        userRepository.saveAll(Arrays.asList(admin, user));
	    
	    // Log in console
	    System.out.println("Database successfully initialized");    
	}

}

