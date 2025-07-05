package com.firefly.auth.web.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class AuthRequest {
    
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,20}$", message = "用户名必须是4-20位的字母、数字或下划线")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$", 
            message = "密码必须是8-20位，包含大小写字母和数字")
    private String password;
    
    @NotBlank(message = "认证方式不能为空")
    @Pattern(regexp = "^(PASSWORD|TOKEN)$", message = "认证方式必须是PASSWORD或TOKEN")
    private String authMethod;
} 