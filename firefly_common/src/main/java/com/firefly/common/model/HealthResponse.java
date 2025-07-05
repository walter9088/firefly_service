package com.firefly.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class HealthResponse {
    private String serviceName;
    private String serviceIp;
    private String status;
    private LocalDateTime timestamp;

    public static HealthResponse ok(String serviceName, String serviceIp) {
        HealthResponse response = new HealthResponse();
        response.setServiceName(serviceName);
        response.setServiceIp(serviceIp);
        response.setStatus("OK");
        response.setTimestamp(LocalDateTime.now());
        return response;
    }
} 