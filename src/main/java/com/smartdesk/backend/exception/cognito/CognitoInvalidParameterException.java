package com.smartdesk.backend.exception.cognito;

import com.smartdesk.backend.exception.NonTransientException;

public class CognitoInvalidParameterException extends NonTransientException {

    public CognitoInvalidParameterException(String message, Throwable cause) {
        super(message);
    }
}
