package com.firefly.auth.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AccountLoginReqDTO {
    @NotBlank(message = "用户名不能为空")
    private String userName;
    
    @NotBlank(message = "密码不能为空")
    private String password;
} 