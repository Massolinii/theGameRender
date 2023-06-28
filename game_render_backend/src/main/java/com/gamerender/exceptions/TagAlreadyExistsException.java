package com.gamerender.exceptions;

@SuppressWarnings("serial")
public class TagAlreadyExistsException extends RuntimeException {
    public TagAlreadyExistsException(String message) {
        super(message);
    }
    
    public TagAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
