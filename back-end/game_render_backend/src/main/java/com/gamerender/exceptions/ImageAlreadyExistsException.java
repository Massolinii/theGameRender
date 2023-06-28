package com.gamerender.exceptions;

@SuppressWarnings("serial")
public class ImageAlreadyExistsException extends RuntimeException {
    public ImageAlreadyExistsException(String message) {
        super(message);
    }
    
    public ImageAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
