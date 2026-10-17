package com.smartdesk.backend.client.hr;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hr-service", url = "${hr.base-url}", fallback = HrClientFallback.class)
public interface HrClient {

    @GetMapping("/api/hr/users/{employeeId}")
    HrUserResponse getUser(@PathVariable("employeeId") String employeeId);
}
