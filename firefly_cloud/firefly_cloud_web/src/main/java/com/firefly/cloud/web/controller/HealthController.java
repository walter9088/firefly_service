package com.firefly.cloud.web.controller;

import com.firefly.common.model.HealthResponse;
import com.firefly.common.model.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping("/isOk")
    public HttpResponse<HealthResponse> isOk() throws Exception {
        String serviceIp = InetAddress.getLocalHost().getHostAddress();
        return HttpResponse.success(HealthResponse.ok(serviceName, serviceIp));
    }
} 