package com.smartdesk.backend.client.hr;

import java.util.UUID;

public record HrUserResponse(
        String employeeId,
        String email,
        String displayName,
        UUID tenantId,
        boolean partial
) {
    public static HrUserResponse partial(String employeeId) {
        return new HrUserResponse(employeeId, null, null, null, true);
    }
}
