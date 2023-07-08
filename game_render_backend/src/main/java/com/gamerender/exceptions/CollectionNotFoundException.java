package com.gamerender.exceptions;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class CollectionNotFoundException extends RuntimeException{
	
	public CollectionNotFoundException(String message) {
        super(message);
    }
	
	public CollectionNotFoundException(String message, Throwable cause) {
        super(message, cause);
	}

	public CollectionNotFoundException(HttpStatus httpStatus, String message) {
		super(message);
	}
}
