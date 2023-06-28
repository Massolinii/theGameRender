package com.gamerender.exceptions;

@SuppressWarnings("serial")
public class UserEmailAlreadyExistsException extends RuntimeException {
	
	public UserEmailAlreadyExistsException(String message) {
        super(message);
    }
	
	public UserEmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
	}
}
