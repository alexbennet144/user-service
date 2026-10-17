package com.smartdesk.backend.exception;

public class ValidationException extends NonTransientException {

    public ValidationException(String message) {
        super(message);
    }
}
