package com.firefly.auth.service.vo;

import lombok.Data;

@Data
public class LoginRspVO {
    private String token;
    private long expiresIn;
} 