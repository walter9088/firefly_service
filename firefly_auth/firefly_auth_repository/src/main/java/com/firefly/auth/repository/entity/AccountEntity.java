package com.firefly.auth.repository.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AccountEntity {
    private Long id;
    private String userName;
    private String nickName;
    private String password;
    private String email;
    private String mobile;
    private String accountStatus;
    private LocalDateTime lastLoginTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 