package com.firefly.auth.web.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.firefly.auth.repository.mapper")
public class MyBatisConfig {
} 