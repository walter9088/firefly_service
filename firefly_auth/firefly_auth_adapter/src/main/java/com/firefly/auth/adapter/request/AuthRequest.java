package com.firefly.auth.adapter.request;

import lombok.Data;

/**
 * 外部认证请求
 */
@Data
public class AuthRequest {
    /**
     * 认证类型
     */
    private String authType;
    
    /**
     * 认证凭证
     */
    private String credential;
    
    /**
     * 认证参数
     */
    private String params;
} 