 package com.smartdesk.backend.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartdesk.backend.model.HelloResponse;
import com.smartdesk.backend.service.HelloService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hello")
@RequiredArgsConstructor
public class HelloController {

    private final HelloService helloService;

    // Public endpoint (no auth)
    @GetMapping("/public")
    public HelloResponse publicHello(@RequestParam(defaultValue = "World") String name) {
        return helloService.sayHello("public " + name);
    }

    // Any authenticated user
    @GetMapping
    public HelloResponse hello(@RequestParam(defaultValue = "World") String name) {
        return helloService.sayHello(name);
    }

    // Requires a specific scope (example: smartdesk-api/device.read)
    @PreAuthorize("hasAuthority('SCOPE_smartdesk-api/device.read') or hasAuthority('SCOPE_device.read')")
    @GetMapping("/device")
    public HelloResponse deviceHello() {
        return helloService.sayHello("device");
    }

    // Requires user to be in Cognito group "ADMIN" (mapped to ROLE_ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public HelloResponse adminHello(@AuthenticationPrincipal Principal principal) {
        return helloService.sayHello("admin");
    }
}
