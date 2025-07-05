package com.firefly.auth.web.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequest {
    
    @NotBlank(message = "刷新令牌不能为空")
    private String refreshToken;
} 