package com.gamerender.exceptions;

@SuppressWarnings("serial")
public class CollectionAlreadyExistsException extends RuntimeException {
    public CollectionAlreadyExistsException(String message) {
        super(message);
    }
    
    public CollectionAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
