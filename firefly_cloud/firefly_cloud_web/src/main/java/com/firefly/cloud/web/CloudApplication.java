package com.firefly.cloud.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.firefly.cloud"})
@MapperScan("com.firefly.cloud.repository.dao")
public class CloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudApplication.class, args);
    }
} 