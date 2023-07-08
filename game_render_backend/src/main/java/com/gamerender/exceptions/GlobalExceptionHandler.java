package com.gamerender.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gamerender.security.exceptions.NoAdminRoleException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	 private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	
	 	@ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
	        // Log the exception
	        logger.error("Data integrity violation", ex);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }

	    @ExceptionHandler(ImageNotFoundException.class)
	    public ResponseEntity<String> handleImageNotFoundException(ImageNotFoundException ex) {
	        // Log the exception
	        logger.error("Image not found", ex);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }

	    @ExceptionHandler(CategoryNotFoundException.class)
	    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException ex) {
	        // Log the exception
	        logger.error("Category not found", ex);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }

	    @ExceptionHandler(CollectionNotFoundException.class)
	    public ResponseEntity<String> handleCollectionNotFoundException(CollectionNotFoundException ex) {
	        // Log the exception
	        logger.error("Collection not found", ex);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }

	    @ExceptionHandler(TagNotFoundException.class)
	    public ResponseEntity<String> handleTagNotFoundException(TagNotFoundException ex) {
	        // Log the exception
	        logger.error("Tag not found", ex);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }
	    
	    @ExceptionHandler(ImageAlreadyExistsException.class)
	    public ResponseEntity<String> handleImageAlreadyExistsException(ImageAlreadyExistsException ex) {
	        // Log the exception
	        logger.error("Image already exist", ex);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	    }
	    
		@ExceptionHandler(UserEmailAlreadyExistsException.class)
		public ResponseEntity<String> handleUserEmailAlreadyExistsException(UserEmailAlreadyExistsException ex) {
	        // Log the exception
	        logger.error("Email already in use", ex);
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
		
		@ExceptionHandler(UsernameAlreadyExistsException.class)
		public ResponseEntity<String> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
	        // Log the exception
	        logger.error("Username already exist", ex);
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
		
		@ExceptionHandler(DataIntegrityViolationException.class)
	    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
	        // Log the exception
	        logger.error("Data integrity violation", ex);

	        // This exception is thrown when there is a violation of a database constraint
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data provided.");
	    }
		
		@ExceptionHandler(NoAdminRoleException.class)
	    public ResponseEntity<String> handleNoAdminRoleException(NoAdminRoleException ex) {
	        // Log the exception
	        logger.error("User not authenticated for this operation.", ex);

	        // This is a general exception handler that will catch any exception not caught by the other handlers
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	    }
		
		@ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleGeneralException(Exception ex) {
	        // Log the exception
	        logger.error("General error", ex);

	        // This is a general exception handler that will catch any exception not caught by the other handlers
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured");
	    }
}
