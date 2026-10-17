package com.smartdesk.backend.exception.cognito;

public abstract class CognitoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected CognitoException(String message) {
        super(message);
    }

    protected CognitoException(String message, Throwable cause) {
        super(message, cause);
    }

    protected CognitoException(Throwable cause) {
        super(cause);
    }
}