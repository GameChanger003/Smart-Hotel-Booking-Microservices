package com.cts.hotel.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String message) {
        super(message);
    }

}
