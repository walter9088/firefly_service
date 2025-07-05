package com.firefly.auth.adapter.response;

import lombok.Data;

/**
 * 外部认证响应
 */
@Data
public class AuthResponse {
    /**
     * 认证结果
     */
    private boolean success;
    
    /**
     * 认证令牌
     */
    private String token;
    
    /**
     * 错误信息
     */
    private String errorMessage;
} 