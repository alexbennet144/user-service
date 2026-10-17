package com.smartdesk.backend.exception.cognito;

import com.smartdesk.backend.exception.TransientException;

public class CognitoServiceUnavailableException extends TransientException {

    public CognitoServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
