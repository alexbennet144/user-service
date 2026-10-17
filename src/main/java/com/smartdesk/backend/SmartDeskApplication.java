package com.smartdesk.backend; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableFeignClients
@EnableKafka
@EnableRetry
public class SmartDeskApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartDeskApplication.class, args);
    }
}
