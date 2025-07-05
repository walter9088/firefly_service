package com.firefly.cloud.service;

import com.firefly.cloud.service.dto.StorageDirDto;
import com.firefly.cloud.service.vo.StorageDirVo;
import java.util.List;

public interface StorageDirService {
    boolean createDir(StorageDirDto storageDirDto);
    boolean updateDir(StorageDirDto storageDirDto);
    boolean deleteDir(Long id);
    StorageDirVo getDir(Long id);
    StorageDirVo getDirByUserIdAndParentDirAndDirName(Long userId, String parentDir, String dirName);
    List<StorageDirVo> getDirsByUserId(Long userId);
} 