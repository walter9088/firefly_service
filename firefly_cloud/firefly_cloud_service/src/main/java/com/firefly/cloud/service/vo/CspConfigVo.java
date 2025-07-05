package com.firefly.cloud.service.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CspConfigVo {
    private Long id;
    private String csp;
    private String bucket;
    private String region;
    private String ossRegion;
    private String internetEndpoint;
    private String intranetEndpoint;
    private String accessKey;
    private String secret;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 