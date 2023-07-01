package com.gamerender.runner;

import com.gamerender.models.*;
import com.gamerender.repository.*;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class MyRunner implements ApplicationRunner {

	private final UserRepository userRepository;
	private final ImageRepository imageRepository;
	private final CollectionRepository collectionRepository;
	private final CategoryRepository categoryRepository;
	private final TagRepository tagRepository;

	// Create class-level variables
	private List<Category> categories;
	private List<Tag> tags;
	private List<Image> images;
	private List<Collection> collections;
	private List<User> users;

	public MyRunner(UserRepository userRepository, ImageRepository imageRepository, 
	                CollectionRepository collectionRepository, CategoryRepository categoryRepository,
	                TagRepository tagRepository) {
	    this.userRepository = userRepository;
	    this.imageRepository = imageRepository;
	    this.collectionRepository = collectionRepository;
	    this.categoryRepository = categoryRepository;
	    this.tagRepository = tagRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
	    // Create some categories
	    Category category1 = new Category("category1");
	    Category category2 = new Category("category2");
	    categoryRepository.save(category1);
	    categoryRepository.save(category2);
	    
	    // Create some collections
	    Collection collection1 = new Collection("Collection 1", category1);;
	    Collection collection2 = new Collection("Collection 2", category2);
	    collectionRepository.save(collection1);
	    collectionRepository.save(collection2);

	    // Create some images
	    Image image1 = new Image("Image 1", "url1", "prompt1", collection1);
	    Image image2 = new Image("Image 2", "url2", "prompt2", collection2);
	    imageRepository.save(image1);
	    imageRepository.save(image2);

	    // Create some tags
	    Tag tag1 = new Tag("Tag 1");
	    tagRepository.save(tag1);

	    // Add tags to images
	    image1.setTags(Arrays.asList(tag1));
	    image2.setTags(Arrays.asList(tag1));
	    imageRepository.save(image1);
	    imageRepository.save(image2);

	    // Check if mail is already present
	    Optional<User> existingUser = userRepository.findByEmail("user1@gmail.com");
	    if (existingUser.isPresent()) {
	        System.out.println("L'indirizzo email è già in uso.");
	        return;
	    }
	    // or else makes random users
	    User user1 = new User("user1", "user1@gmail.com", "password1", "firstname1", "lastname1");
	    User user2 = new User("user2", "user2@gmail.com", "password2", "firstname2", "lastname2");
	    userRepository.save(user1);
	    userRepository.save(user2);

	    // Add favorite images to user's collections
	    user1.setFavoriteImages(new HashSet<>(Arrays.asList(image1)));
	    user2.setFavoriteImages(new HashSet<>(Arrays.asList(image2)));
	    userRepository.save(user1);
	    userRepository.save(user2);

	    // Log a console
	    System.out.println("Database inizializzato con successo!");
	}
}