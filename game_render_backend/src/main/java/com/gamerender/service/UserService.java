package com.gamerender.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gamerender.exceptions.UserEmailAlreadyExistsException;
import com.gamerender.exceptions.UserNotFoundException;
import com.gamerender.exceptions.UsernameAlreadyExistsException;
import com.gamerender.models.Image;
import com.gamerender.models.User;
import com.gamerender.repositories.ImageRepository;
import com.gamerender.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired private UserRepository userRepository;
	
	@Autowired private ImageRepository imageRepository;
	
	public Set<Image> getUserFavoriteImages(String username) {
	    Optional<User> user = userRepository.findByUsername(username);
	    if (user.isPresent()) {
	        return user.get().getFavoriteImages();
	    } else {
	        throw new UserNotFoundException("User not found with username: " + username);
	    }
	}
	
	public User createUser(User user) {
		 try {
		        return userRepository.save(user);
		    } catch (DataIntegrityViolationException ex) {
		        if (ex.getCause() instanceof UserEmailAlreadyExistsException) {
		            throw new UserEmailAlreadyExistsException("User with email " + user.getEmail() + " already exists.", ex);
		        } else if (ex.getCause() instanceof UsernameAlreadyExistsException) {
		            throw new UsernameAlreadyExistsException("User with name " + user.getUsername() + " already exists.", ex);
		        }
		        throw ex;
		    }
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
        		.orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new UserNotFoundException("User with ID " + user.getId() + " not found.");
        }
        return userRepository.save(user);
    }

    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
        userRepository.deleteById(id);
        return "Goodbye friend.";
    }
    
    public User toggleFavoriteImage(String username, Long imageId) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Image> imageOptional = imageRepository.findById(imageId);

        if(userOptional.isPresent() && imageOptional.isPresent()) {
            User user = userOptional.get();
            Image image = imageOptional.get();

            if(user.getFavoriteImages().contains(image)) {
                user.getFavoriteImages().remove(image);
            } else {
                user.getFavoriteImages().add(image);
            }
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User or image not found");
        }
    }
	
}