package com.smartdesk.backend.exception;

public class NonTransientException extends RuntimeException {

    public NonTransientException(String message) {
        super(message);
    }
}
