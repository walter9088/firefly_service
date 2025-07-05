package com.firefly.cloud.service.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CspConfigDto {
    /**
     * 存储桶唯一标识
     */
    private Long id;
    
    /**
     * 存储桶
     */
    private String csp;
    
    /**
     * 存储桶
     */
    private String bucket;
    
    /**
     * 区域
     */
    private String region;
    
    /**
     * oss区域
     */
    private String ossRegion;
    
    /**
     * 外网访问endpoint
     */
    private String internetEndpoint;
    
    /**
     * 内网访问endpoint
     */
    private String intranetEndpoint;
    
    /**
     * 令牌ak
     */
    private String accessKey;
    
    /**
     * 令牌秘钥
     */
    private String secret;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 