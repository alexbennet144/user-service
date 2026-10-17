package com.smartdesk.backend.exception.cognito;

import com.smartdesk.backend.exception.TransientException;

public class CognitoUnavailableException extends TransientException {

    public CognitoUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
