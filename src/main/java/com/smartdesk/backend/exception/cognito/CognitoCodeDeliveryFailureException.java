package com.smartdesk.backend.exception.cognito;

import com.smartdesk.backend.exception.NonTransientException;

public class CognitoCodeDeliveryFailureException extends NonTransientException {

    public CognitoCodeDeliveryFailureException(String message, Throwable cause) {
        super(message);
    }
}
