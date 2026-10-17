package com.smartdesk.backend.exception.cognito;

import com.smartdesk.backend.exception.TransientException;

public class CognitoTooManyRequestsException extends TransientException {

    public CognitoTooManyRequestsException(String message, Throwable cause) {
        super(message, cause);
    }
}
