package com.gamerender.exceptions;

@SuppressWarnings("serial")
public class FavoriteNotFoundException extends RuntimeException{
	
	public FavoriteNotFoundException(String message) {
        super(message);
    }
	
	public FavoriteNotFoundException(String message, Throwable cause) {
        super(message, cause);
	}
}