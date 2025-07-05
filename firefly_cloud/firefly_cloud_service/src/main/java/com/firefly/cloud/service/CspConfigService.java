package com.firefly.cloud.service;

import com.firefly.cloud.service.dto.CspConfigDto;
import com.firefly.cloud.service.vo.CspConfigVo;
import java.util.List;

public interface CspConfigService {
    boolean createConfig(CspConfigDto configDto);
    boolean updateConfig(CspConfigDto configDto);
    boolean deleteConfig(Long id);
    CspConfigVo getConfig(Long id);
    CspConfigVo getConfigByBucket(String bucket);
    List<CspConfigVo> getAllConfigs();
} 