package com.firefly.auth.web.response;

import lombok.Data;

@Data
public class AuthResponse {
    
    private boolean success;
    
    private String userId;
    
    private String accessToken;
    
    private String refreshToken;
    
    private Long expireTime;
    
    private String errorCode;
    
    private String errorMessage;
} 