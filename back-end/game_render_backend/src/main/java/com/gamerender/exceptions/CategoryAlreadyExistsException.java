package com.gamerender.exceptions;

@SuppressWarnings("serial")
public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
    
    public CategoryAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
