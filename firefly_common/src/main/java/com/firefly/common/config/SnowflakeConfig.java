package com.firefly.common.config;

import com.firefly.common.util.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {
    
    @Value("${snowflake.datacenter-id:1}")
    private long datacenterId;
    
    @Value("${snowflake.machine-id:1}")
    private long machineId;
    
    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator(datacenterId, machineId);
    }
} 