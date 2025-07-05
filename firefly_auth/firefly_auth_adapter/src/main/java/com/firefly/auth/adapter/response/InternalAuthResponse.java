package com.firefly.auth.adapter.response;

import lombok.Data;

/**
 * 内部认证响应
 */
@Data
public class InternalAuthResponse {
    /**
     * 认证结果
     */
    private boolean success;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 访问令牌
     */
    private String accessToken;
    
    /**
     * 刷新令牌
     */
    private String refreshToken;
    
    /**
     * 令牌过期时间
     */
    private long expireTime;
    
    /**
     * 错误码
     */
    private String errorCode;
    
    /**
     * 错误信息
     */
    private String errorMessage;
} 