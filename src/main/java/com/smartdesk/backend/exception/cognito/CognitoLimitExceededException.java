package com.smartdesk.backend.exception.cognito;

import com.smartdesk.backend.exception.TransientException;

public class CognitoLimitExceededException extends TransientException {

    public CognitoLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
