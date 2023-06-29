package com.gamerender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gamerender.exceptions.UserEmailAlreadyExistsException;
import com.gamerender.exceptions.UserNotFoundException;
import com.gamerender.models.Image;
import com.gamerender.models.User;
import com.gamerender.repository.UserRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class UserService {
	
	@Autowired private UserRepository userRepository;
	@Autowired private ImageService imageService;
	
	public User createUser(User user) {
		 try {
		        return userRepository.save(user);
		    } catch (DataIntegrityViolationException ex) {
		        if (ex.getCause() instanceof ConstraintViolationException) {
		            throw new UserEmailAlreadyExistsException("User with email " + user.getEmail() + " already exists.", ex);
		        }
		        throw ex;
		    }
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
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

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
        userRepository.deleteById(id);
    }

    public List<User> findUsersByLikedImage(Long imageId) {
    	Image image = imageService.findImageById(imageId);
        return userRepository.findUsersByFavoriteImages(image);
    }
	
}
