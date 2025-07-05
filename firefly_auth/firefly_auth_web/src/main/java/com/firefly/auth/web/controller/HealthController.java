package com.firefly.auth.web.controller;

import com.firefly.common.model.HealthResponse;
import com.firefly.common.model.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/isOk")
    public HttpResponse<HealthResponse> isOk() {
        try {
            String serviceIp = InetAddress.getLocalHost().getHostAddress();
            HealthResponse healthResponse = new HealthResponse()
                    .setServiceName("firefly-auth")
                    .setServiceIp(serviceIp)
                    .setStatus("UP")
                    .setTimestamp(LocalDateTime.now());
            return HttpResponse.success(healthResponse);
        } catch (Exception e) {
            return HttpResponse.serverError("获取服务信息失败", e.getMessage());
        }
    }
} 