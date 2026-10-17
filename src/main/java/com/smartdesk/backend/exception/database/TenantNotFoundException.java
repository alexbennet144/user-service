package com.smartdesk.backend.exception.database;

import com.smartdesk.backend.exception.NonTransientException;

public class TenantNotFoundException extends NonTransientException {

    public TenantNotFoundException(String message) {
        super(message);
    }
}
