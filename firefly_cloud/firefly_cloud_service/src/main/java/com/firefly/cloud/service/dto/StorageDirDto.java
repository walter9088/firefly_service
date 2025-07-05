package com.firefly.cloud.service.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StorageDirDto {
    private Long id;
    private Long userId;
    private String parentDir;
    private String dirName;
    private String source;
    private LocalDateTime createTime;
    private String updateTime;
} 