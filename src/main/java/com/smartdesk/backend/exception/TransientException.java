package com.smartdesk.backend.exception;

public class TransientException extends RuntimeException {

    public TransientException(String message, Throwable cause) {
        super(message, cause);
    }
}
