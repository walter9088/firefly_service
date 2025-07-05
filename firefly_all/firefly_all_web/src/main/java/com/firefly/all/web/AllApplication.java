package com.firefly.all.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.firefly.auth",
    "com.firefly.cloud"
})
public class AllApplication {
    public static void main(String[] args) {
        SpringApplication.run(AllApplication.class, args);
    }
} 