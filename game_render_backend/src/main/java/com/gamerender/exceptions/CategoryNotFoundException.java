package com.gamerender.exceptions;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class CategoryNotFoundException extends RuntimeException{
	
	public CategoryNotFoundException(HttpStatus notFound, String message) {
        super(message);
    }
	
	public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
	}

	public CategoryNotFoundException(String message) {
		super(message);
	}
}

