package com.firefly.auth.service.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class AccountRegisterReqDTO {
    @NotBlank(message = "用户名不能为空")
    private String userName;
    
    @NotBlank(message = "昵称不能为空")
    private String nickName;
    
    @NotBlank(message = "密码不能为空")
    private String password;
    
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;
} 