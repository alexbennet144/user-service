package com.smartdesk.backend.exception.cognito;

import com.smartdesk.backend.exception.NonTransientException;

public class CognitoUnsupportedUserStateException extends NonTransientException {

    public CognitoUnsupportedUserStateException(String message, Throwable cause) {
        super(message);
    }
}
