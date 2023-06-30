package com.gamerender.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	 private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	
	 	@ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }

	    @ExceptionHandler(ImageNotFoundException.class)
	    public ResponseEntity<String> handleImageNotFoundException(ImageNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }

	    @ExceptionHandler(CategoryNotFoundException.class)
	    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }

	    @ExceptionHandler(CollectionNotFoundException.class)
	    public ResponseEntity<String> handleCollectionNotFoundException(CollectionNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }

	    @ExceptionHandler(FavoriteNotFoundException.class)
	    public ResponseEntity<String> handleFavoriteNotFoundException(FavoriteNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }

	    @ExceptionHandler(TagNotFoundException.class)
	    public ResponseEntity<String> handleTagNotFoundException(TagNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }
	    
	    @ExceptionHandler(ImageAlreadyExistsException.class)
	    public ResponseEntity<String> handleImageAlreadyExistsException(ImageAlreadyExistsException ex) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	    }
	    
		@ExceptionHandler(UserEmailAlreadyExistsException.class)
		public ResponseEntity<String> handleUserEmailAlreadyExistsException(UserEmailAlreadyExistsException ex) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}

		@ExceptionHandler(DataIntegrityViolationException.class)
	    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
	        // Log the exception
	        logger.error("Data integrity violation", ex);

	        // This exception is thrown when there is a violation of a database constraint
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data provided.");
	    }

		@ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleGeneralException(Exception ex) {
	        // Log the exception
	        logger.error("General error", ex);

	        // This is a general exception handler that will catch any exception not caught by the other handlers
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
	    }
}
