package com.ecoquest.game.exception; // Adjust the package based on your project's structure

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
