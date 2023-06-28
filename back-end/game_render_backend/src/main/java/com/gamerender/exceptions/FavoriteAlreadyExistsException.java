package com.gamerender.exceptions;

@SuppressWarnings("serial")
public class FavoriteAlreadyExistsException extends RuntimeException {
    public FavoriteAlreadyExistsException(String message) {
        super(message);
    }
    
    public FavoriteAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
