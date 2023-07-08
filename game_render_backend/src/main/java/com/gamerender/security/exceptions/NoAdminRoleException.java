package com.gamerender.security.exceptions;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class NoAdminRoleException extends RuntimeException{
	
	public NoAdminRoleException(HttpStatus notFound, String message) {
        super(message);
    }
	
	public NoAdminRoleException(String message, Throwable cause) {
        super(message, cause);
	}

	public NoAdminRoleException(String message) {
		super(message);
	}
}

