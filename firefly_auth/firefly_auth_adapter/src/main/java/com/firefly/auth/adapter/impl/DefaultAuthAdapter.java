package com.firefly.auth.adapter.impl;

import com.firefly.auth.adapter.AuthAdapter;
import com.firefly.auth.adapter.request.AuthRequest;
import com.firefly.auth.adapter.request.InternalAuthRequest;
import com.firefly.auth.adapter.response.AuthResponse;
import com.firefly.auth.adapter.response.InternalAuthResponse;
import org.springframework.stereotype.Component;

/**
 * 默认认证适配器实现
 */
@Component
public class DefaultAuthAdapter implements AuthAdapter {

    @Override
    public InternalAuthRequest adapt(AuthRequest request) {
        InternalAuthRequest internalRequest = new InternalAuthRequest();
        // 根据外部请求类型转换为内部请求
        switch (request.getAuthType()) {
            case "password":
                internalRequest.setAuthMethod("PASSWORD");
                internalRequest.setAuthParams(request.getParams());
                break;
            case "token":
                internalRequest.setAuthMethod("TOKEN");
                internalRequest.setAuthParams(request.getCredential());
                break;
            default:
                internalRequest.setAuthMethod("UNKNOWN");
                internalRequest.setAuthParams(request.getParams());
        }
        return internalRequest;
    }

    @Override
    public AuthResponse adapt(InternalAuthResponse response) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setSuccess(response.isSuccess());
        if (response.isSuccess()) {
            authResponse.setToken(response.getAccessToken());
        } else {
            authResponse.setErrorMessage(response.getErrorMessage());
        }
        return authResponse;
    }
} 