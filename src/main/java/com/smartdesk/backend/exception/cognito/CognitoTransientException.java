package com.smartdesk.backend.exception.cognito;

public class CognitoTransientException extends CognitoException {

    private static final long serialVersionUID = 1L;

    public CognitoTransientException(String message) {
        super(message);
    }

    public CognitoTransientException(String message, Throwable cause) {
        super(message, cause);
    }

    public CognitoTransientException(Throwable cause) {
        super(cause);
    }
}