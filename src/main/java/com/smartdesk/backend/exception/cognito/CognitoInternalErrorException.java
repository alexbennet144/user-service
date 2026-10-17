package com.smartdesk.backend.exception.cognito;

import com.smartdesk.backend.exception.TransientException;

public class CognitoInternalErrorException extends TransientException {

    public CognitoInternalErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
