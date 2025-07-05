package com.firefly.cloud.service.impl;

import com.firefly.cloud.repository.dao.StorageDirDao;
import com.firefly.cloud.service.dto.StorageDirDto;
import com.firefly.cloud.service.vo.StorageDirVo;
import com.firefly.cloud.service.StorageDirService;
import com.firefly.cloud.repository.entity.StorageDirEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageDirServiceImpl implements StorageDirService {
    
    @Autowired
    private StorageDirDao storageDirDao;
    
    @Override
    @Transactional
    public boolean createDir(StorageDirDto storageDirDto) {
        StorageDirEntity entity = convertToEntity(storageDirDto);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now().toString());
        return storageDirDao.insert(entity) > 0;
    }
    
    @Override
    @Transactional
    public boolean updateDir(StorageDirDto storageDirDto) {
        StorageDirEntity entity = convertToEntity(storageDirDto);
        entity.setUpdateTime(LocalDateTime.now().toString());
        return storageDirDao.update(entity) > 0;
    }
    
    @Override
    @Transactional
    public boolean deleteDir(Long id) {
        return storageDirDao.deleteById(id) > 0;
    }
    
    @Override
    public StorageDirVo getDir(Long id) {
        StorageDirEntity entity = storageDirDao.findById(id);
        return convertToVo(entity);
    }
    
    @Override
    public StorageDirVo getDirByUserIdAndParentDirAndDirName(Long userId, String parentDir, String dirName) {
        StorageDirEntity entity = storageDirDao.findByUserIdAndParentDirAndDirName(userId, parentDir, dirName);
        return convertToVo(entity);
    }
    
    @Override
    public List<StorageDirVo> getDirsByUserId(Long userId) {
        List<StorageDirEntity> entities = storageDirDao.findByUserId(userId);
        return entities.stream().map(this::convertToVo).collect(Collectors.toList());
    }

    private StorageDirEntity convertToEntity(StorageDirDto dto) {
        StorageDirEntity entity = new StorageDirEntity();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setParentDir(dto.getParentDir());
        entity.setDirName(dto.getDirName());
        entity.setSource(dto.getSource());
        entity.setCreateTime(dto.getCreateTime());
        entity.setUpdateTime(dto.getUpdateTime());
        return entity;
    }

    private StorageDirVo convertToVo(StorageDirEntity entity) {
        StorageDirVo vo = new StorageDirVo();
        vo.setId(entity.getId());
        vo.setUserId(entity.getUserId());
        vo.setParentDir(entity.getParentDir());
        vo.setDirName(entity.getDirName());
        vo.setSource(entity.getSource());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }
} 