package com.smartdesk.backend.exception.cognito;

import com.smartdesk.backend.exception.NonTransientException;

public class CognitoNotAuthorizedException extends NonTransientException {

    public CognitoNotAuthorizedException(String message, Throwable cause) {
        super(message);
    }
}
