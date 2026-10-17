package com.smartdesk.backend.exception.cognito;

import com.smartdesk.backend.exception.NonTransientException;

public class CognitoInvalidPasswordException extends NonTransientException {

    public CognitoInvalidPasswordException(String message, Throwable cause) {
        super(message);
    }
}
