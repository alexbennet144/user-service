package com.smartdesk.backend.client.hr;

import org.springframework.stereotype.Component;

@Component
public class HrClientFallback implements HrClient {

    @Override
    public HrUserResponse getUser(String employeeId) {
        return HrUserResponse.partial(employeeId);
    }
}
