package com.smartdesk.backend.exception.cognito;

public class CognitoPermanentException extends CognitoException {

    private static final long serialVersionUID = 1L;

    public CognitoPermanentException(String message) {
        super(message);
    }

    public CognitoPermanentException(String message, Throwable cause) {
        super(message, cause);
    }

    public CognitoPermanentException(Throwable cause) {
        super(cause);
    }
}