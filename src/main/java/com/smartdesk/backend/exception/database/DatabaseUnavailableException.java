package com.smartdesk.backend.exception.database;

import com.smartdesk.backend.exception.TransientException;

public class DatabaseUnavailableException extends TransientException {

    public DatabaseUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
