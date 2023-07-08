package com.gamerender.exceptions;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class UsernameAlreadyExistsException extends RuntimeException {
	
	public UsernameAlreadyExistsException(String message) {
        super(message);
    }
	
	public UsernameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
	}

	public UsernameAlreadyExistsException(HttpStatus badRequest, String message) {
		super(message);
	}
}
