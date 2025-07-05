package com.firefly.cloud.service.impl;

import com.firefly.cloud.service.CspConfigService;
import com.firefly.cloud.service.dto.CspConfigDto;
import com.firefly.cloud.service.vo.CspConfigVo;
import com.firefly.cloud.repository.dao.CspConfigDao;
import com.firefly.cloud.repository.entity.CspConfigEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CspConfigServiceImpl implements CspConfigService {
    
    @Autowired
    private CspConfigDao cspConfigDao;
    
    @Override
    @Transactional
    public boolean createConfig(CspConfigDto configDto) {
        CspConfigEntity entity = convertToEntity(configDto);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        return cspConfigDao.insert(entity) > 0;
    }
    
    @Override
    @Transactional
    public boolean updateConfig(CspConfigDto configDto) {
        CspConfigEntity entity = convertToEntity(configDto);
        entity.setUpdateTime(LocalDateTime.now());
        return cspConfigDao.update(entity) > 0;
    }
    
    @Override
    @Transactional
    public boolean deleteConfig(Long id) {
        return cspConfigDao.deleteById(id) > 0;
    }
    
    @Override
    public CspConfigVo getConfig(Long id) {
        CspConfigEntity entity = cspConfigDao.findById(id);
        return convertToVo(entity);
    }
    
    @Override
    public CspConfigVo getConfigByBucket(String bucket) {
        CspConfigEntity entity = cspConfigDao.findByBucket(bucket);
        return convertToVo(entity);
    }
    
    @Override
    public List<CspConfigVo> getAllConfigs() {
        List<CspConfigEntity> entities = cspConfigDao.findAll();
        return entities.stream().map(this::convertToVo).collect(Collectors.toList());
    }
    
    private CspConfigEntity convertToEntity(CspConfigDto dto) {
        if (dto == null) return null;
        CspConfigEntity entity = new CspConfigEntity();
        entity.setId(dto.getId());
        entity.setCsp(dto.getCsp());
        entity.setBucket(dto.getBucket());
        entity.setRegion(dto.getRegion());
        entity.setOssRegion(dto.getOssRegion());
        entity.setInternetEndpoint(dto.getInternetEndpoint());
        entity.setIntranetEndpoint(dto.getIntranetEndpoint());
        entity.setAccessKey(dto.getAccessKey());
        entity.setSecret(dto.getSecret());
        entity.setCreateTime(dto.getCreateTime());
        entity.setUpdateTime(dto.getUpdateTime());
        return entity;
    }
    
    private CspConfigVo convertToVo(CspConfigEntity entity) {
        if (entity == null) return null;
        CspConfigVo vo = new CspConfigVo();
        vo.setId(entity.getId());
        vo.setCsp(entity.getCsp());
        vo.setBucket(entity.getBucket());
        vo.setRegion(entity.getRegion());
        vo.setOssRegion(entity.getOssRegion());
        vo.setInternetEndpoint(entity.getInternetEndpoint());
        vo.setIntranetEndpoint(entity.getIntranetEndpoint());
        vo.setAccessKey(entity.getAccessKey());
        vo.setSecret(entity.getSecret());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }
} 