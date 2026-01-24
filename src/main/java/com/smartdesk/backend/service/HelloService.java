package com.smartdesk.backend.service;

import com.smartdesk.backend.model.HelloResponse;
import org.springframework.stereotype.Service;

/**
 * Simple service — put business logic here.
 */
@Service
public class HelloService {

    public HelloResponse sayHello(String name) {
        return new HelloResponse("Hello, " + name + "!");
    }
}
