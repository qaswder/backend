package com.example.backendlib.error;

public class ConflictResourceException extends RuntimeException{
    public ConflictResourceException(String message) {
        super(message);
    }
}
