package com.firefly.auth.service.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountRspVO {
    private Long id;
    private String userName;
    private String nickName;
    private String email;
    private String mobile;
    private String accountStatus;
    private LocalDateTime lastLoginTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 