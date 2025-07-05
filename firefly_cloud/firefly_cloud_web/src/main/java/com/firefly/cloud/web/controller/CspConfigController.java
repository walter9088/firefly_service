package com.firefly.cloud.web.controller;

import com.firefly.cloud.service.CspConfigService;
import com.firefly.cloud.service.dto.CspConfigDto;
import com.firefly.cloud.service.vo.CspConfigVo;
import com.firefly.common.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/csp/config")
@Validated
public class CspConfigController {
    
    @Autowired
    private CspConfigService cspConfigService;
    
    @PostMapping
    public HttpResponse<Boolean> createConfig(@RequestBody @Valid CspConfigDto configDto) {
        boolean success = cspConfigService.createConfig(configDto);
        return HttpResponse.success(success);
    }
    
    @PutMapping
    public HttpResponse<Boolean> updateConfig(@RequestBody @Valid CspConfigDto configDto) {
        boolean success = cspConfigService.updateConfig(configDto);
        return HttpResponse.success(success);
    }
    
    @DeleteMapping("/{id}")
    public HttpResponse<Boolean> deleteConfig(@PathVariable Long id) {
        boolean success = cspConfigService.deleteConfig(id);
        return HttpResponse.success(success);
    }
    
    @GetMapping("/{id}")
    public HttpResponse<CspConfigVo> getConfig(@PathVariable Long id) {
        CspConfigVo configVo = cspConfigService.getConfig(id);
        return HttpResponse.success(configVo);
    }
    
    @GetMapping("/bucket/{bucket}")
    public HttpResponse<CspConfigVo> getConfigByBucket(@PathVariable String bucket) {
        CspConfigVo configVo = cspConfigService.getConfigByBucket(bucket);
        return HttpResponse.success(configVo);
    }
    
    @GetMapping("/list")
    public HttpResponse<List<CspConfigVo>> getAllConfigs() {
        List<CspConfigVo> configs = cspConfigService.getAllConfigs();
        return HttpResponse.success(configs);
    }
} 