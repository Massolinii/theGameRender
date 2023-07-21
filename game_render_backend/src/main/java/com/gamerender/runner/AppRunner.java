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
		// Initializing categories
		Category category_environment = null;
		Category category_object = null;
		Category category_chars = null;
		
		// Initializing collections
		// FIRST CATEGORY : Environment's collections
		Collection natural = null;
		Collection medieval = null;
		Collection asian = null;
		Collection scifi = null;
		Collection misc = null;
		// SECOND CATEGORY : Object's collections
		Collection tools = null;
		Collection items = null;
		Collection gemsstones = null;	
		// THIRD CATEGORY : Character's collections
		Collection heroes = null;
		Collection baddies = null;
		Collection npc = null;
		
		// Initializing images
		// NATURAL images
		Image image1 = null;
		// SIMPLEBUILDING images
		Image image2 = null;
		// SCIFI images
		Image image3 = null;
		
		// TOOLS images;
		Image image4 = null;
		// PROPS images;
		Image image5 = null;
		// COLLECTIBLES images;
		Image image6 = null;
		
		// HEROES images
		Image image7 = null;
		// BADDIES images
		Image image8 = null;
		// NPC images
		Image image9 = null;
		    
	    // Check if categories already exist
	    if (categoryRepository.count() > 0) {
	        System.out.println("Categories already exist - No need to initialize categories.");
	    } else {
	       // Create some categories
	    	category_environment = new Category("Environments");
	    	category_object = new Category("Objects");
	    	category_chars = new Category("Characters");
	        categoryRepository.saveAll(Arrays.asList(category_environment, category_object, category_chars));
	    }
	    
	    // Check if collections already exist
	    if (collectionRepository.count() > 0) {
	        System.out.println("Collections already exist - No need to initialize collections.");
	    } else {
	        // Create some collections
	    	natural = new Collection("Natural", category_environment);
	    	medieval = new Collection("Medieval", category_environment);
	    	asian = new Collection("Asian", category_environment);
	    	scifi = new Collection("Sci-Fi", category_environment);
	    	misc = new Collection("Miscellaneous", category_environment);
	    	collectionRepository.saveAll(Arrays.asList(natural, medieval, scifi, asian, misc));
	    	
	    	tools = new Collection("Tools", category_object);
	    	items = new Collection("Props", category_object);
	    	gemsstones = new Collection("Gems and Stones", category_object);
	    	collectionRepository.saveAll(Arrays.asList(tools, items, gemsstones));
	    	
	    	heroes = new Collection("Heroes", category_chars);
	    	baddies = new Collection("Baddies", category_chars);
	    	npc = new Collection("Npc", category_chars);
	    	collectionRepository.saveAll(Arrays.asList(heroes, baddies, npc));
	    }
	    
	    // Check if images already exist
	    if (imageRepository.count() > 0) {
	        System.out.println("Images already exist - No need to initialize images.");
	    } else {
	        // Create some images with tags
	         image1 = new Image("http://res.cloudinary.com/dfqlb8zrq/image/upload/v1688982308/pbggzjxqpdahddtsmbys.jpg",
	        		 "game assets for a mmorpg game, plants textures, high detail, hd",
	        		 natural, new HashSet<>(Arrays.asList("light", "realistic")));
	         image2 = new Image("http://res.cloudinary.com/dfqlb8zrq/image/upload/v1688986805/pvdpaz3zdafjublrbbix.jpg",
	        		 "seperated game asset, isometric view of a small factory on a square patch of grass, cut out game asset, cory loftis, james gilleard, atey ghailan, makoto shinkai, goro fujita, studio ghibli, rim light, exquisite lighting, clear focus, very coherent, plain background, high definition",
	        		 medieval, new HashSet<>(Arrays.asList("cozy", "cartoon", "stone")));
	         image3 = new Image("http://res.cloudinary.com/dfqlb8zrq/image/upload/v1688987064/tdp3jzwvjeauq5tn7yu7.webp",
	        		 "game asset of exterior modular, in gouache detailed paintings, props, stylized, 2 d sprites, kitbash, arcane, overwatch, blue and pink color scheme, 8 k, close up",
	        		 scifi, new HashSet<>(Arrays.asList("blue", "purple", "alien", "stone")));
	         image4 = new Image("http://res.cloudinary.com/dfqlb8zrq/image/upload/v1688999832/qjq5yqeksnxazryewyre.jpg",
	        		 "polygonal 3d model of an old traditional japanese house, in the style of hauntingly beautiful illustrations, 2d game art, brushwork exploration, realistic and hyper-detailed renderings, isometric, delicately rendered landscapes, dark teal and light orange",
	        		 asian, new HashSet<>(Arrays.asList("blue", "purple", "alien", "stone")));
	         image5 = new Image("http://res.cloudinary.com/dfqlb8zrq/image/upload/v1689000228/cydkwcpgasmwh8vej5bt.jpg",
	        		 "polygonal 3d model of a game item, a big hammer, bright and vivid colorful, 2d game art, 3D rendering, C4D",
	    	         tools, new HashSet<>(Arrays.asList("magic, weapon, tool, kingdom hearts, spelunky")));
	        
	        		
	        //image1 = new Image("",
	        //		 "",
	        //		 natural, new HashSet<>(Arrays.asList("")));
	        imageRepository.saveAll(Arrays.asList(image1, image2, image3, image4, image5));
	    }

	 // Add favorite images to user's collections
//        User admin = userRepository.findByEmail("masso.lini@epicode.com").orElseThrow(() -> new RuntimeException("Admin not found"));
//        User user = userRepository.findByEmail("lucy.parsons@epicode.com").orElseThrow(() -> new RuntimeException("User not found"));
//        admin.setFavoriteImages(new HashSet<>(Arrays.asList(image1, image2, image3)));
//        user.setFavoriteImages(new HashSet<>(Arrays.asList(image4, image5, image6)));
//        userRepository.saveAll(Arrays.asList(admin, user));
	    
	    // Log in console
	    System.out.println("Database successfully initialized");    
	}

}

