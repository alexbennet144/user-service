package com.smartdesk.backend.exception;

public class InvalidUserDataException extends NonTransientException {

    public InvalidUserDataException(String message) {
        super(message);
    }
}
