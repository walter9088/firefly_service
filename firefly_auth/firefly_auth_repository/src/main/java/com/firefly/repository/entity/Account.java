package com.firefly.repository.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Account {
    private Integer userId;
    private String username;
    private String passwordHash;
    private String email;
    private String mobile;
    private String accountStatus;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime lastLogin;
} 