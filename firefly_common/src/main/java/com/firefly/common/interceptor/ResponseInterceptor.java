package com.firefly.common.interceptor;

import com.firefly.common.model.HttpResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseInterceptor implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {
        // 如果返回类型不是HttpResponse，直接返回
        if (!(body instanceof HttpResponse)) {
            return body;
        }

        HttpResponse<?> httpResponse = (HttpResponse<?>) body;
        
        // 设置响应头
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        
        // 根据code设置HTTP状态码
        HttpStatus status = mapToHttpStatus(httpResponse.getCode());
        response.setStatusCode(status);
        
        // 返回原始响应体
        return body;
    }
    
    private HttpStatus mapToHttpStatus(int code) {
        switch (code) {
            case 200:
                return HttpStatus.OK;
            case 400:
                return HttpStatus.BAD_REQUEST;
            case 401:
                return HttpStatus.UNAUTHORIZED;
            case 403:
                return HttpStatus.FORBIDDEN;
            case 404:
                return HttpStatus.NOT_FOUND;
            case 500:
                return HttpStatus.INTERNAL_SERVER_ERROR;
            default:
                return HttpStatus.OK;
        }
    }
} 