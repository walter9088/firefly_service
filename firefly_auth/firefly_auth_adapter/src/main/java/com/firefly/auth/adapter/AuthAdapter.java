package com.firefly.auth.adapter;

import com.firefly.auth.adapter.request.AuthRequest;
import com.firefly.auth.adapter.request.InternalAuthRequest;
import com.firefly.auth.adapter.response.AuthResponse;
import com.firefly.auth.adapter.response.InternalAuthResponse;

/**
 * 认证适配器接口
 * 用于将外部系统的认证方式适配到内部统一的认证接口
 */
public interface AuthAdapter {
    
    /**
     * 适配外部认证请求
     *
     * @param request 外部认证请求
     * @return 内部认证请求
     */
    InternalAuthRequest adapt(AuthRequest request);

    /**
     * 适配内部认证响应
     *
     * @param response 内部认证响应
     * @return 外部认证响应
     */
    AuthResponse adapt(InternalAuthResponse response);
} 