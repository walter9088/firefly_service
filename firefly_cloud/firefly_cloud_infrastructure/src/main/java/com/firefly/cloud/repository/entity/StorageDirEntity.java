package com.firefly.cloud.repository.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StorageDirEntity {
    private Long id;
    private Long userId;
    private String parentDir;
    private String dirName;
    private String source;
    private LocalDateTime createTime;
    private String updateTime;
} 