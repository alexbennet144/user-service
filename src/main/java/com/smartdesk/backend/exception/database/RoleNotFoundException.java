package com.smartdesk.backend.exception.database;

import com.smartdesk.backend.exception.NonTransientException;

public class RoleNotFoundException extends NonTransientException {

    public RoleNotFoundException(String message) {
        super(message);
    }
}
