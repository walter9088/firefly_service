package com.firefly.auth.adapter.request;

import lombok.Data;

/**
 * 内部认证请求
 */
@Data
public class InternalAuthRequest {
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 认证方式
     */
    private String authMethod;
    
    /**
     * 认证参数
     */
    private String authParams;
} 