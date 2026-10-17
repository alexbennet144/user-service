package com.smartdesk.backend.exception.database;

import com.smartdesk.backend.exception.NonTransientException;

public class DuplicateUserException extends NonTransientException {

    public DuplicateUserException(String message, Throwable cause) {
        super(message);
    }
}
